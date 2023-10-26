import "./App.css";
import SignInPage from "./pages/SignInPage";
import SignUpPage from "./pages/SignUpPage";
import HomePage from "./pages/HomePage";
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import Bar from "./components/Bar";
import { createContext, useState } from "react";

export const UserContext = createContext(null);

function App() {
  const [userInfo, setUserInfo] = useState(null);
  return (
    <UserContext.Provider value={{ userInfo, setUserInfo }}>
      <div className="App">
        <Router>
          <Bar />
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/signup" element={<SignUpPage />} />
            <Route path="/signin" element={<SignInPage />} />
          </Routes>
        </Router>
      </div>
    </UserContext.Provider>
  );
}

export default App;
