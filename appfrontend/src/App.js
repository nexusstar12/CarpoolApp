import logo from './logo.svg';
import './App.css';
import Bar from './components/Bar';
import SignIn from './components/SignIn';
import SignInPage from './pages/SignInPage';
import SignUp from './components/SignUp';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import { useState } from 'react';

function App() {
  return (
    <div className="App">
      <Router>
      <Bar/>
      <Routes>
              <Route path="/signup" element={<SignUp/>} />
              <Route path="/signin" element={<SignInPage/>} />
      </Routes>
    </Router>
    </div>
  );
}


export default App;



