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
import OutagePage from "./pages/OutagePage";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
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
            <Route path="/create-pool" element={<PoolCreationPage />} />
            <Route path="/terms-and-conditions" element={<TermsAndConditions />} />
            <Route
              path="/passenger-profile"
              element={<PassengerProfilePage />}
            />
            <Route path="/driver-profile" element={<DriverProfilePage />} />
            <Route path="/my-pools" element={<ListPoolPage />} />
            <Route path="/my-crews" element={<ListCrewPage />} />
            <Route path="/down" element={<OutagePage />} />
          </Routes>
        </Router>
      </div>
    </UserContext.Provider>
  );
}

export default App;
