import React, {useState,useRef,useHistory} from "react";
import {BrowserRouter, Routes, Route, Link} from 'react-router-dom';
import {useNavigate } from "react-router-dom";
import logo from './AccoliteLogo.png';
import './login.css'
import { useAuth } from "./context/AuthContext";

const Register = () => {


    const emailRef = useRef()
  const passwordRef = useRef()
  const passwordConfirmRef = useRef()
  const { signup } = useAuth() || {}
  const [error, setError] = useState("")
  const history = useNavigate()
  const [loading, setLoading] = useState(false)
   async function handleSubmit(e) {
        e.preventDefault()
    
        if (passwordRef.current.value !== passwordConfirmRef.current.value) {
          return setError("Passwords do not match")
        }
    
        try {
          setError("")
          setLoading(true)

          await signup( emailRef.current.value, passwordRef.current.value)
          history("/login")
        } catch {
          setError("Failed to create an account")
        }
        setLoading(false)
       
      }



    const renderForm = (
      <div className="form">
        <form onSubmit={handleSubmit}>
          <div className="input-container">
            <label>Email </label>
            <input type="email" name="email" ref={emailRef} required />
          </div>
          <div className="input-container">
            <label>Password </label>
            <input type="password" name="pass" ref={passwordRef} required />
          </div>
          <div className="input-container">
            <label>Confirm Password </label>
            <input type="password" name="confpass" ref={passwordConfirmRef} required />
          </div>
          <div className="button-container">
            <input type="submit" name="submitBtn"/>
          </div>
        </form>
      </div>
    );
    return (
      <div className="app">
      <div className="login-form">
      <img src={logo} alt="Logo" />

        <div className="title">Sign Up</div>
       {renderForm}
      </div>
      Already have an account? <Link to="/login">Login here!</Link>

    </div>
    );
}

export default Register;