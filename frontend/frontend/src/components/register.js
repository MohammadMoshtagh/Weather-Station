import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import register from '../services/api'

const Register = (props) => {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')

  const navigate = useNavigate()

  const onButtonClick = async () => {
    try {
      const response = await register ({ username, password });
      console.log(response)
    //   if (response.data.token) {
    //     // localStorage.setItem('authToken', response.data.token);
    //     //navigate('/login');
    //   } else {
    //     alert('Invalid credentials');
    //   }
    } catch (error) {
      console.error('Error register in:', error);
    }
  }

  return (
    <div className={'mainContainer'}>
      <div className={'titleContainer'}>
        <div>Register</div>
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
        <input className={'inputButton'} type="button" onClick={onButtonClick} value={'Register'} />
      </div>
    </div>
  )
}

export default Register