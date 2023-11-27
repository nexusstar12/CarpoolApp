import React from "react";
import CarRepairIcon from "@mui/icons-material/CarRepair";
import { Typography } from "@mui/material";

const OutagePage = () => (
  <div style={{ display: "flex" }}>
    <div style={{ flex: 1 }}>
      <img
        src="outage_page_image.jpg"
        style={{ width: "110%", height: "130%" }}
        alt="Car under repair"
      />
    </div>
    <div style={{ flex: 1, padding: "300px 1px 16px", textAlign: "center" }}>
      <CarRepairIcon style={{ fontSize: 100, marginBottom: "16px", color: "#0b7bc7" }} />
      <Typography variant="h4">Pool is temporarily unavailable</Typography>
      <Typography variant="body1">Please try again later.</Typography>
    </div>
  </div>
);

export default OutagePage;
