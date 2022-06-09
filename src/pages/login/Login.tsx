import React, {useState, useRef} from "react";
import {BrowserRouter, Routes, Route, Link} from 'react-router-dom';
import {useNavigate } from "react-router-dom";
import logo from './AccoliteLogo.png';
import { SlackLoginButton,FacebookLoginButton, GoogleLoginButton } from "react-social-login-buttons";
import { useAuth } from "../../context/AuthContext";
import TextInput from "@src/components/text-input/text-input-component";
import Button from "@src/components/button/button-component";
import { NextPage } from "next";

interface LoginPageProps {

}

const LoginPage:NextPage<LoginPageProps>= () => {

  const emailRef = useRef()
  const passwordRef = useRef()
  // const { login } = useAuth()
  const [error, setError] = useState("")
  const [loading, setLoading] = useState(false)
  const history = useNavigate()
  const [userName, setUserName] = useState("")
  const [password, setPassword] = useState("")

  async function handleSubmit(e:any) {
    e.preventDefault()

    try {
      setError("")
      setLoading(true)
      await login(userName, password)
      history('/home')

    } catch {
      setError("Failed to log in")
    }

    setLoading(false)
  }




    const renderForm = (
      <div className="form">
        <form onSubmit={handleSubmit}>
          <TextInput inputLabel="Email" inputName="uname" inputValue={userName}/>
          <TextInput inputLabel="Password" inputName="pass" inputValue={password}/> 
          <Button buttonName="Login" onClick={handleSubmit}/>  
        </form>
      </div>
    );
    return (
      <div className="app">
      <div className="login-form">
      {/* <img src={logo} alt="Logo" /> */}

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

export default LoginPage;