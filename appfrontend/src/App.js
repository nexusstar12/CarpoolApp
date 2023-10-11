import "./App.css";
import SignInPage from "./pages/SignInPage";
import SignUpPage from "./pages/SignUpPage";
import HomePage from "./pages/HomePage";
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import Bar from "./components/Bar";

function App() {
  return (
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
  );
}

export default App;
