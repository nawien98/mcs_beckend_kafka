import React, { useState, useRef } from "react";
import { Link } from 'react-router-dom';
import { SlackLoginButton, FacebookLoginButton, GoogleLoginButton } from "react-social-login-buttons";
import TextInput from "@src/components/text-input/text-input-component";
import Button from "@src/components/button/button-component";
import { NextPage } from "next";

type LoginPageProps = {

}

const LoginPage: NextPage<LoginPageProps> = () => {
  const [userName, setUserName] = useState("")
  const [password, setPassword] = useState("")

  return (
   <>
      <TextInput inputLabel="Email" inputName="uname" inputValue={userName} />
      <TextInput inputLabel="Password" inputName="pass" inputValue={password} />
     
    </>
      
  );
}

export default LoginPage;