import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { login } from '../services/api'

const Login = (props) => {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')

  const navigate = useNavigate()

  const onButtonClick = async () => {
    try {
        const response = await login({ username, password });
        console.log(response)
        if (response.data.token) {
          localStorage.setItem('authToken', response.data.token);
          //navigate('/admin');
        } else {
          alert('Invalid credentials');
        }
      } catch (error) {
        console.error('Error logging in:', error);
      }
  }

  return (
    <div className={'mainContainer'}>
      <div className={'titleContainer'}>
        <div>Login</div>
      </div>
      <br />
      <div className={'inputContainer'}>
        <input
          value={username}
          placeholder="Enter your username here"
          onChange={(ev) => setUsername(ev.target.value)}
          className={'inputBox'}
        />
      </div>
      <br />
      <div className={'inputContainer'}>
        <input
          value={password}
          placeholder="Enter your password here"
          onChange={(ev) => setPassword(ev.target.value)}
          className={'inputBox'}
        />
      </div>
      <br />
      <div className={'inputContainer'}>
        <input className={'inputButton'} type="button" onClick={onButtonClick} value={'Log in'} />
      </div>
    </div>
  )
}

export default Login