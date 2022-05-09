import React, {useState} from "react";
import {BrowserRouter, Routes, Route, Link} from 'react-router-dom';
import {useNavigate } from "react-router-dom";
import logo from './AccoliteLogo.png';
import './login.css'
import { SlackLoginButton,FacebookLoginButton, GoogleLoginButton,LinkedInLoginButton } from "react-social-login-buttons";


const Login = () => {
    const renderForm = (
      <div className="form">
        <form>
          <div className="input-container">
            <label>Username </label>
            <input type="text" name="uname" required />
          </div>
          <div className="input-container">
            <label>Password </label>
            <input type="password" name="pass" required />
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