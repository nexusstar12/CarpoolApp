import React, { useContext } from "react";
import { Paper, Typography, Box } from "@mui/material";
import { UserContext } from "../App";
import HailIcon from "@mui/icons-material/Hail";
import PersonIcon from "@mui/icons-material/Person";
import Hail from "@mui/icons-material/Hail";

const PassengerProfile = () => {
  const userContext = useContext(UserContext);

  const containerStyle = {
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    justifyContent: "center",
    minHeight: "80vh",
  };

  const profileStyle = {
    border: "1px solid #ddd",
    padding: "70px",
    boxShadow: "0 0 5px rgba(0, 0, 0, 0.2)",
    textAlign: "center",
  };

  const iconStyle = {
    fontSize: "100px",
  };

  const nameStyle = {
    fontSize: "36px",
  };

  return (
    <Box sx={containerStyle}>
      <Hail fontSize="small" sx={{ color: "#0b7bc7", ...iconStyle }} />
      <Typography variant="h6" gutterBottom sx={nameStyle}>
        Passenger Profile
      </Typography>

      <Paper sx={profileStyle}>
        <Typography>
          <strong>Name:</strong> {userContext.userInfo.name}
        </Typography>
        <Typography>
          <strong>PhoneNumber:</strong> {userContext.userInfo.phoneNumber}
        </Typography>
        <Typography>
          <strong>Email:</strong> {userContext.userInfo.email}
        </Typography>
      </Paper>
    </Box>
  );
};

export default PassengerProfile;
