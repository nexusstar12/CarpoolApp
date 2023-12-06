/* eslint-disable jsx-a11y/img-redundant-alt */
import SearchBar from "../components/SearchBar";
import React, { useState, useEffect } from "react";
import axiosInstance from "../config/axios.config";
import { SearchResult } from "../components/SearchResult";
import CssBaseline from "@mui/material/CssBaseline";
import { createTheme, ThemeProvider, useTheme } from "@mui/material/styles";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";
import useMediaQuery from "@mui/material/useMediaQuery";
import CookieConsent from "../components/CookieConsent";
import { Typography } from "@mui/material";
import { useNavigate } from "react-router-dom";
import Box from "@mui/material/Box";
import Modal from "@mui/material/Modal";
import { useLocation } from "react-router-dom";

const defaultTheme = createTheme();

function HomePage() {
  const history = useNavigate();
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down("sm"));
  const [search, setSearch] = useState("");
  const [result, setResult] = useState([]);
  const [notFound, setNotFound] = useState(false);
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const location = useLocation();
  useEffect(() => {
    if (location.state?.showSuccessModal) {
      setShowSuccessModal(true);
    }
  }, [location.state]);

  const handleSearchSubmit = async ({ searchQuery, filterOption }) => {
    setSearch({ searchQuery, filterOption });
    try {
      const data = await axiosInstance.get(
        `/searchbar?filter=${filterOption}&value=${searchQuery}`
      );

      if (data.data.length > 0) {
        setResult(data.data);
      } else {
        setNotFound(true);
        setResult([]);
      }
    } catch (error) {
      if (error.response.status >= 500) {
        history("/down");
      }
    }
  };

  const SuccessModal = () => {
    return (
      <Modal
        open={showSuccessModal}
        onClose={() => setShowSuccessModal(false)}
        aria-labelledby="success-modal-title"
        aria-describedby="success-modal-description"
      >
        <Box
          sx={{
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            width: 400,
            bgcolor: "background.paper",
            boxShadow: 24,
            p: 4,
            alignItems: "center",
            display: "flex",
            justifyContent: "center",
            flexDirection: "column",
          }}
        >
          <Typography id="success-modal-title" variant="h6" component="h2">
            Logout Successful!
          </Typography>
        </Box>
      </Modal>
    );
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <SuccessModal />
      <Grid
        container
        component="main"
        direction={"row"}
        sx={{ height: "85vh" }}
      >
        <CssBaseline />
        <Grid
          item
          sm={0}
          md={6}
          lg={6}
          elevation={6}
          sx={{
            // Use an object for media queries
            backgroundImage: { xs: "url(homepage_carpool.jpg)" },
            backgroundRepeat: "no-repeat",
            backgroundColor: (t) =>
              t.palette.mode === "light"
                ? t.palette.grey[50]
                : t.palette.grey[900],
            backgroundSize: "cover",
            backgroundPosition: "center",
            display: { xs: "none", sm: "none", md: "block" },
          }}
        />
        <Grid
          item
          xs={12}
          sm={12}
          md={6}
          lg={6}
          component={Paper}
          elevation={6}
          square
          sx={{
            width: "100%",
            margin: 0,
            display: "block",
            justifyContent: "center",
          }}
        >
          <div className="home-page-container">
            <div className="search-container">
              <h1 className="home-page-title" style={{ textAlign: "center" }}>
                Get there, together.
              </h1>
              <Grid
                item
                xs={12}
                sm={8}
                md={6}
                lg={12}
                style={{
                  display: "block",
                  justifyContent: "center",
                  margin: "auto",
                }}
                className="search-input"
              >
                <SearchBar
                  onSearch={handleSearchSubmit}
                  style={{ width: isMobile ? "100%" : "auto" }}
                />
              </Grid>

              <div className="search-results-container">
                {result.length > 0 && (
                  <SearchResult
                    className="search-result"
                    result={result}
                    itemsPerPage={10}
                  />
                )}
                {notFound && (
                  <Typography>
                    No pools found. Try searching for a pool in San Francisco.
                  </Typography>
                )}
              </div>
            </div>
          </div>
        </Grid>
      </Grid>
      <CookieConsent />
    </ThemeProvider>
  );
}

export default HomePage;
