import './App.css';
import Home from "./Home";
import Login from "./login";


import {BrowserRouter, Routes, Route, Link} from 'react-router-dom';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
      
       
        <Routes>
            <Route path="/login" element={<Login/>} />
        </Routes>
        <Routes>
            <Route path="/home" element={<Home/>} />
        </Routes>
        
    </BrowserRouter>
      
    </div>
  );
}

export default App;
