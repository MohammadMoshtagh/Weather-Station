import logging
from country_service import CountryService

import telebot
from telebot.types import ReplyKeyboardMarkup, KeyboardButton, BotCommand, InlineKeyboardMarkup, InlineKeyboardButton

# Enable logging
logging.basicConfig(format='%(asctime)s - %(name)s - %(levelname)s - %(message)s', level=logging.INFO)
logger = logging.getLogger(__name__)

COUNTRIES_PER_PAGE = 10

class CountryBot:

    def __init__(self, country_service: CountryService):
        self.country_service = country_service

        # Pagination state management
        self.user_states = {}

    def handle(self):
        bot = telebot.TeleBot("7081006800:AAG_xcElMM90owiEsXmZr5omuevh4xT_cJg")
        commands = [
            BotCommand(command="/start", description="Start the bot and get a welcome message"),
            BotCommand(command="/help", description="Show help message with all commands"),
            BotCommand(command="/countries", description="Get a list of all countries"),
            BotCommand(command="/country", description="Get details about a specific country")
        ]

        # Create a custom keyboard
        def create_menu():
            markup = ReplyKeyboardMarkup(resize_keyboard=True)
            markup.add(KeyboardButton('/help'))
            markup.add(KeyboardButton('/countries'))
            return markup

        # Command handler for /start
        @bot.message_handler(commands=['start'])
        def send_welcome(message):
            bot.set_my_commands(commands, scope=telebot.types.BotCommandScopeChat(message.chat.id))
            bot.send_message(message.chat.id, 'Hi! Use the menu below to navigate the bot.', reply_markup=create_menu())

        # Command handler for /help
        @bot.message_handler(commands=['help'])
        def send_help(message):
            help_text = (
                "Available commands:\n"
                "/start - Start the bot and get a welcome message\n"
                "/help - Show this help message\n"
                "/countries - Get a list of all countries\n"
                "/country <name> - Get details about a specific country\n"
            )
            bot.send_message(message.chat.id, help_text)

        # Handler for pagination buttons
        @bot.callback_query_handler(func=lambda call: call.data in ['next_page', 'prev_page'])
        def handle_pagination(call):
            current_page = self.user_states.get(call.message.chat.id, 0)
            if call.data == 'next_page':
                current_page += 1
            elif call.data == 'prev_page':
                current_page -= 1

            self.user_states[call.message.chat.id] = current_page
            country_names = self.country_service.get_countries(current_page)
            if country_names:
                bot.edit_message_text(chat_id=call.message.chat.id, message_id=call.message.message_id, text=f'List of countries:', reply_markup=create_pagination_keyboard(current_page, self.calculate_total_pages(), country_names))

        # Handler for pagination buttons
        @bot.callback_query_handler(func=lambda call: call.data not in ['next_page', 'prev_page'])
        def handle_country(call):
            country_name = call.data
            country = self.country_service.get_country(country_name)
            if country:
                country_names = self.country_service.get_countries(self.user_states[call.message.chat.id])
                bot.edit_message_text(chat_id=call.message.chat.id, message_id=call.message.message_id, text=country, reply_markup=create_pagination_keyboard(self.user_states[call.message.chat.id], self.calculate_total_pages(), country_names))
            else:
                bot.edit_message_text(chat_id=call.message.chat.id, message_id=call.message.message_id, text=f"Country {country_name} not found!", reply_markup=create_menu())

        # Function to generate the inline keyboard for pagination
        def create_pagination_keyboard(page, total_pages, list_countries):
            keyboard = InlineKeyboardMarkup()
            for i in range(len(list_countries)):
                if i % 2 == 1:
                    continue
                keyboard.add(InlineKeyboardButton(list_countries[i], callback_data=list_countries[i]), InlineKeyboardButton(list_countries[i + 1], callback_data=list_countries[i + 1]))
            if page > 0 and page < total_pages - 1:
                keyboard.add(InlineKeyboardButton('Previous', callback_data='prev_page'), InlineKeyboardButton('Next', callback_data='next_page'))
            elif page > 0:
                keyboard.add(InlineKeyboardButton('Previous', callback_data='prev_page'))
            elif page < total_pages - 1:
                keyboard.add(InlineKeyboardButton('Next', callback_data='next_page'))
            return keyboard

        # Command handler for /countries
        @bot.message_handler(commands=['countries'])
        def list_countries(message):
            self.user_states[message.chat.id] = 0  # Initialize user page state
            country_names = self.country_service.get_countries(page_num = 0)
            if country_names:
                bot.send_message(message.chat.id, f'List of countries:', reply_markup=create_pagination_keyboard(0, self.calculate_total_pages(), country_names))
            else:
                bot.send_message(message.chat.id, 'Failed to retrieve country list.')

        @bot.message_handler(commands=['country'])
        def fetch_country(message):
            args = message.text.split()[1:]
            if args:
                country_name = ' '.join(args)
                country = self.country_service.get_country(country_name)
                if country:
                    bot.reply_to(message, country)
                else:
                    bot.reply_to(message, f"Country {country_name} not found!")
            else:
                bot.send_message(message.chat.id, 'Please add country name!')

        bot.polling()

    def calculate_total_pages(self):
        return (self.country_service.total_countries + COUNTRIES_PER_PAGE - 1) // COUNTRIES_PER_PAGE


if __name__ == '__main__':
    country_service = CountryService()
    country_bot = CountryBot(country_service)
    country_bot.handle()
