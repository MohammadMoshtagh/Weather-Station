from locust import HttpUser, SequentialTaskSet, task, between

class UserBehavior(SequentialTaskSet):

    # @task
    # def register(self):
    #     self.client.post("users/register", json={
    #         "username": "test",
    #         "password": "password123"
    #     })

    @task
    def login(self):
        self.client.post("users/login", json={
            "username": "admin",
            "password": "admin"
        })
        self.wait()

    def on_start(self):
        response = self.client.post("users/login", json={
            "username": "admin",
            "password": "admin"
        })
        self.user.token = response.headers.get("Authorization")
        print(f"Login response status code: {response.status_code}")
        print(f"Login response content: {response.text}")
        print(f"Captured Authorization Token: {self.user.token}") 


    @task
    def get_country_by_name(self):
        headers = {
            "Authorization": self.user.token
        }
        self.client.get("countries/iran", headers=headers)
        self.wait()

    @task
    def get_country_weather_by_name(self):
        headers = {
            "Authorization": self.user.token
        }
        self.client.get("countries/iran/weather", headers=headers)
        self.wait()

    @task
    def get_user_list(self):
        headers = {
            "Authorization": self.user.token
        }
        self.client.get("admin/users", headers=headers)
        self.wait()

    def wait(self):
        self.user.wait_time()

class WebsiteUser(HttpUser):
    tasks = [UserBehavior]
    wait_time = between(1, 5)
