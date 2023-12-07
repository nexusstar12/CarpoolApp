import React from "react";
import { Box, Container, Grid, Typography } from "@mui/material";
import Link from "@mui/material/Link";
import "./Footer.css";

const Footer = () => {
  return (
    <Box
      sx={{
        width: "100%",
        height: "10vh",
        backgroundColor: "#1679c9",
        paddingTop: "0.5rem",
        paddingBottom: "1.5rem",
        lineHeight: "px",
      }}
      className="footer"
    >
      <Container maxWidth="lg">
        <Grid container direction="row" alignItems="center">
          <Grid item xs={12}></Grid>
          <Grid item xs={12}>
            <Typography color="#FFFFFF" variant="subtitle1">
              <Link
                href="/terms-and-conditions"
                className="FooterText"
                lineHeight={"2.4"}
              >
                Terms and Conditions
              </Link>
            </Typography>
          </Grid>
        </Grid>
        <Typography
          color="#FFFFFF"
          variant="h2"
          fontSize={"15px"}
          fontWeight={"bold"}
        >
          Region & Language: United States - English
        </Typography>
      </Container>
    </Box>
  );
};

export default Footer;
