/* eslint-disable jsx-a11y/img-redundant-alt */
import SearchBar from "../components/SearchBar";
import React, { useState } from "react";
import axiosInstance from "../config/axios.config";
import { SearchResult } from "../components/SearchResult";
import CssBaseline from "@mui/material/CssBaseline";
import { createTheme, ThemeProvider, useTheme } from "@mui/material/styles";
import Grid from "@mui/material/Grid";
import Paper from "@mui/material/Paper";
import useMediaQuery from "@mui/material/useMediaQuery";

const defaultTheme = createTheme();

function HomePage() {
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down("sm"));
  const [search, setSearch] = useState("");
  const [result, setResult] = useState([]);

  const handleSearchSubmit = async ({ searchQuery, filterOption }) => {
    setSearch({ searchQuery, filterOption });

    const data = await axiosInstance.get(
      `/searchbar?filter=${filterOption}&value=${searchQuery}`
    );
    setResult(data.data);
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Grid
        container
        component="main"
        direction={"row"}
        sx={{ height: "100vh" }}
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
              {!isMobile && (
                <h1 style={{ fontSize: "50px", textAlign: "center" }}>
                  Get there, together.
                </h1>
              )}{" "}
              <Grid item xs={12} sm={8} md={6} lg={12}>
                <SearchBar
                  onSearch={handleSearchSubmit}
                  style={{ width: isMobile ? "100%" : "auto" }}
                />
              </Grid>
              {result.length > 0 ? (
                <div className="search-results-container">
                  <SearchResult
                    className="search-result"
                    result={result}
                    itemsPerPage={10}
                  />
                </div>
              ) : (
                <div className="search-results-empty"></div>
              )}
            </div>
          </div>
        </Grid>
      </Grid>
    </ThemeProvider>
  );
}
/* <div className="home-page-container">
      
      <div className="search-container">
        <h1 style={{ fontSize: "50px" }}>Get there, together.</h1>
        <SearchBar onSearch={handleSearchSubmit} />
        {result.length > 0 ? (
          <div className="search-results-container">
            <SearchResult
              className="search-result"
              result={result}
              itemsPerPage={10}
            />
          </div>
        ) : (
          <div className="search-results-empty"></div>
        )}
      </div>
    </div> */

export default HomePage;
