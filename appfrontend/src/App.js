import "./App.css";
import SignInPage from "./pages/SignInPage";
import SignUpPage from "./pages/SignUpPage";
import HomePage from "./pages/HomePage";
import PoolCreationPage from "./pages/PoolCreationPage";
import PassengerProfilePage from "./pages/PassengerProfilePage";
import DriverProfilePage from "./pages/DriverProfilePage";
import ListPoolPage from "./pages/ListPoolPage";
import ListCrewPage from "./pages/ListCrewPage";
import TermsAndConditions from "./pages/TermsAndConditions";
import AboutPage from "./pages/aboutPage";
import OutagePage from "./pages/OutagePage";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Bar from "./components/Bar";
import Footer from "./components/Footer";
import NotFound from "./pages/NotFoundPage";

import { createContext, useState, useEffect } from "react";

export const UserContext = createContext(null);

function App() {
  const [userInfo, setUserInfo] = useState(() => {
    const storedUserInfo = localStorage.getItem("userInfo");
    return storedUserInfo ? JSON.parse(storedUserInfo) : null;
  });

  return (
    <UserContext.Provider value={{ userInfo, setUserInfo }}>
      <div className="App">
        <Router>
          <Bar />
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/signup" element={<SignUpPage />} />
            <Route path="/signin" element={<SignInPage />} />
            <Route path="/create-pool" element={<PoolCreationPage />} />
            <Route
              path="/terms-and-conditions"
              element={<TermsAndConditions />}
            />
            <Route
              path="/passenger-profile"
              element={<PassengerProfilePage />}
            />
            <Route path="/driver-profile" element={<DriverProfilePage />} />
            <Route path="/my-pools" element={<ListPoolPage />} />
            <Route path="/my-crews" element={<ListCrewPage />} />
            <Route path="/down" element={<OutagePage />} />
            <Route path="/about" element={<AboutPage />} />
            <Route path="*" element={<NotFound />} />{" "}
            {/* Corrected catch-all Route */}
          </Routes>
        </Router>

        <div className="content" />
        <Footer />
        <div />
      </div>
    </UserContext.Provider>
  );
}

export default App;
