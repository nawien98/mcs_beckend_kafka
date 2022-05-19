import React, {useState, useRef} from "react";
import {BrowserRouter, Routes, Route, Link} from 'react-router-dom';
import {useNavigate } from "react-router-dom";
import logo from './AccoliteLogo.png';
import './login.css'
import { SlackLoginButton,FacebookLoginButton, GoogleLoginButton } from "react-social-login-buttons";
import { useAuth } from "./context/AuthContext";


const Login = () => {

  const emailRef = useRef()
  const passwordRef = useRef()
  const { login } = useAuth()
  const [error, setError] = useState("")
  const [loading, setLoading] = useState(false)
  const history = useNavigate()

  async function handleSubmit(e) {
    e.preventDefault()

    try {
      setError("")
      setLoading(true)
      await login(emailRef.current.value, passwordRef.current.value)
      history('/home')

    } catch {
      setError("Failed to log in")
    }

    setLoading(false)
  }




    const renderForm = (
      <div className="form">
        <form onSubmit={handleSubmit}>
          <div className="input-container">
            <label>Email </label>
            <input type="text" name="uname" ref={emailRef} required />
          </div>
          <div className="input-container">
            <label>Password </label>
            <input type="password" name="pass" ref={passwordRef} required />
          </div>
          <div className="button-container">
            <input type="submit" />
          </div>
        </form>
      </div>
    );
    return (
      <div className="app">
      <div className="login-form">
      <img src={logo} alt="Logo" />

        <div className="title">Sign In</div>
       {renderForm}
       <label>Or </label>
       <GoogleLoginButton onClick={() => alert("Google Login")} />

       <FacebookLoginButton onClick={() => alert("FB Login")} />
       <SlackLoginButton onClick={() => alert('Hello')} />


      </div>
      Don't have an account? <Link to="/register">Register here!</Link>

    </div>
    );
}

export default Login;