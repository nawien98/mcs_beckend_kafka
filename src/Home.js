import React,{useContext} from "react";
import {useNavigate } from "react-router-dom";
import { browserHistory } from "react-router";
import './Home.css'
import logo from './AccoliteLogo.png';


  function Home() {
    
    return (
      <div className="app">
        <div className="img">
              <img src={logo} alt="Logo" />
              </div>
              <div className="headingHome">
              <h1>Microservices Accelerator</h1>
              </div>
      <div className="loginform">
          
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
    <label>Orchestration : 
  <select defaultValue="Select Orchestration">
  <option defaultValue>Select Orchestration</option>
                        <option value="Kubernetes">Kubernetes</option>
                    </select>
    </label>
    <label>Security : 
  <select defaultValue="Select Security">
  <option defaultValue>Select Security</option>
                        <option value="SpringSecurity">Spring Security</option>
                        <option value="MicronautSecurity">Micronaut Security</option>
                        <option value="QuarkusSecurity">Quarkus Security</option>

                    </select>
    </label>
    <label>Authentication : 
  <select defaultValue="Select Authentication">
  <option defaultValue>Select Authentication</option>
                        <option value="JWT">JWT</option>
                        <option value="OAuth">OAuth</option>

                    </select>
    </label>
    <label>Tracing : 
  <select defaultValue="Select Tracing">
  <option defaultValue>Select Tracing</option>
                        <option value="OpenTracing">OpenTracing</option>
                    </select>
    </label>
    <label>Monitoring : 
  <select defaultValue="Select Monitoring">
  <option defaultValue>Select Monitoring</option>
                        <option value="Grafana">Grafana</option>
                    </select>
    </label>
    <label>Logging : 
  <select defaultValue="Select Logging">
  <option defaultValue>Select Logging</option>
                        <option value="Slf4j">Slf4j</option>
                        <option value="Logstash">Logstash</option>

                    </select>
    </label>
    <label>Databases  :
  <select defaultValue="Select Databases">
  <option defaultValue>Select Databases</option>

                        <option value="Oracle">Oracle</option>
                        <option value="MySQL">MySQL</option>
                        <option value="MongoDB">MongoDB</option>
                        <option value="SQLServer">SQLServer</option>
                        <option value="Postgres">Postgres</option>
                    </select>
    </label>





</div>
<div className="button-container">
  <input type="submit" value="Submit" /></div>
</form>
</div>
      </div></div>
    );
  }
  
  
  



export default Home;