import React, {useState} from "react";
import {BrowserRouter, Routes, Route, Link} from 'react-router-dom';
import {useNavigate } from "react-router-dom";
import logo from './AccoliteLogo.png';
import './login.css'


const Login = () => {
    let history=useNavigate ();
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
      </div>
    </div>
    );
}

export default Login;