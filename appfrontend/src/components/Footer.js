import React from "react";
import { Box, Container, Grid, Typography } from "@mui/material";
import Link from "@mui/material/Link";
import "./Footer.css";

const Footer = () => {
  return (
    <Box
      sx={{
        width: "100%",
        height: "40px",
        backgroundColor: "#1679c9",
        paddingTop: "0.5rem",
        paddingBottom: "1.5rem",
        lineHeight : "px",
      }}
    >
      <Container maxWidth="lg">
        <Grid container direction="column" alignItems="center">
          <Grid item xs={12}>
            <Typography color="black" variant="h5">
              Pool
            </Typography>
          </Grid>
          <Grid item xs={12}>
            <Typography color="#FFFFFF" variant="subtitle1">
            <Link href="/terms-and-conditions" className = "FooterText">
                Terms and Conditions</Link>
            </Typography>
          </Grid>
        </Grid>
      </Container>
    </Box>
  );
};

export default Footer;