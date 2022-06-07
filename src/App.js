import './App.css';
import Home from "./Home";
import Login from "./login";
import Register from "./register";
import { useAuth } from "./context/AuthContext";
import {AuthProvider} from "./context/AuthContext";


import {BrowserRouter, Routes, Route, Link, Navigate} from 'react-router-dom';

function App() {
  return (
    <div className="App">
    <AuthProvider>
      <BrowserRouter>
      <Routes>
            <Route exact path="/home" element={<Home/>} />
        </Routes>
        <Routes>
            <Route path="/login" element={<Login/>} />
        </Routes>
        <Routes>
            <Route path="/register" element={<Register/>} />
        </Routes>
        <Routes>
        <Route path="/" element={<Navigate replace to="/login" />} />
        </Routes>

    </BrowserRouter>
    </AuthProvider>
    </div>
  );
}

export default App;
