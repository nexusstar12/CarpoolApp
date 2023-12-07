import React, { useContext } from "react";
import { Paper, Typography, Box } from "@mui/material";
import { UserContext } from "../App";
import DirectionsCarIcon from "@mui/icons-material/DirectionsCar";

const DriverProfile = () => {
  const userContext = useContext(UserContext);

  const containerStyle = {
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    justifyContent: "center",
    height: "80vh",
  };

  const profileStyle = {
    border: "1px solid #ddd",
    padding: "70px",
    boxShadow: "0 0 5px rgba(0, 0, 0, 0.2)",
    textAlign: "center",
  };

  const nameStyle = {
    fontSize: "36px",
  };

  const isFastTrakVerified = userContext.userInfo.isFastTrakVerified
    ? "Yes"
    : "No";

  return (
    <Box sx={containerStyle}>
      <DirectionsCarIcon
        fontSize="large"
        sx={{ color: "#0b7bc7", fontSize: "100px" }}
      />
      <Typography variant="h6" gutterBottom sx={nameStyle}>
        Driver Profile
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
        <Typography>
          <strong>FastTrak Verification:</strong> {isFastTrakVerified}
        </Typography>
      </Paper>
    </Box>
  );
};

export default DriverProfile;
