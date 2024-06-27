import React from 'react'
import { useNavigate } from 'react-router-dom'

const Home = (props) => {
  const { loggedIn, username } = props
  const navigate = useNavigate()

  const onButtonClickLogin = () => {
    if (loggedIn) {
        localStorage.removeItem('user')
        props.setLoggedIn(false)
      } else {
        navigate('/login')
      }
  }

  const onButtonClickRegister = () => {
    navigate('/register')
  }

  return (
    <div className="mainContainer">
      <div className={'titleContainer'}>
        <div>Welcome!</div>
      </div>
      <div>This is the home page.</div>
      <div className={'buttonContainer'}>
      <input
          className={'inputButton'}
          type="button"
          onClick={onButtonClickRegister}
          value={'Register'}
        />
        <input
          className={'inputButton'}
          type="button"
          onClick={onButtonClickLogin}
          value={loggedIn ? 'Log out' : 'Log in'}
        />
        
        {loggedIn ? <div>Your username address is {username}</div> : <div />}
      </div>
    </div>
  )
}

export default Home