import React,{useContext} from "react";
import {useNavigate } from "react-router-dom";
import { browserHistory } from "react-router";
import './Home.css'
import logo from './AccoliteLogo.png';


  function Home() {
    
    return (
      <div className="app">
              <img src={logo} alt="Logo" />

      <div className="login-form">
        <div>
          <h1>Microservices Accelerator</h1>
          <div className="form">
           <form>
           <div className="input-container">
           <label>Language : 
  <select defaultValue="Select Language">
  <option defaultValue>Select Language</option>
                        <option value="Java">Java</option>
                        <option value="Kotlin">Kotlin</option>
                        <option value=".NET">.NET</option>

                    </select>
    </label>
  
  <label>Framework  :
  <select defaultValue="Select Framework">
  <option defaultValue>Select Framework</option>

                        <option value="SpringBoot">Spring Boot</option>
                        <option value="Micronaut">Micronaut</option>
                        <option value="Quarkus">Quarkus</option>
                        <option value="Ktor">Ktor</option>
                        <option value="HelidonSE">Helidon SE</option>
                        <option value=".NETCore">.NETCore</option>
                    </select>
    </label>

  <label>Build : 
  <select defaultValue="Select Build">
  <option defaultValue>Select Build</option>
                        <option value="Maven">Maven</option>
                        <option value="Gradle">Gradle</option>

                    </select>
    </label>
 
  <label>Deploy : 
  <select defaultValue="Select Deploy">
  <option defaultValue>Select Deploy</option>
                        <option value="Docker">Docker</option>
                        <option value="Podman">Podman</option>

                    </select>
    </label>
</div>
<div className="button-container">
  <input type="submit" value="Submit" /></div>
</form>
</div>
      </div>
      </div></div>
    );
  }
  
  
  



export default Home;