import React, { useState, useEffect } from "react";
import Snackbar from "@mui/material/Snackbar";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import IconButton from "@mui/material/IconButton";
import CloseIcon from "@mui/icons-material/Close";
import Link from "@mui/material/Link";
import { styled } from "@mui/material/styles";

const StyledLink = styled(Link)(({ theme }) => ({
  cursor: "pointer",
  textDecoration: "none",
  "&:hover": {
    textDecoration: "underline",
    color: theme.palette.secondary.main, // or any color that suits your design
  },
}));

const CookieConsent = () => {
  const [open, setOpen] = useState(false);

  useEffect(() => {
    const consent = localStorage.getItem("cookieConsent");
    if (consent === null) {
      setOpen(true); // Show consent if no choice has been made
    }
  }, []);

  const handleClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }
    setOpen(false);
  };

  const handleConsent = (consent) => {
    localStorage.setItem("cookieConsent", consent);
    setOpen(false);
  };

  return (
    <Snackbar
      anchorOrigin={{
        vertical: "bottom",
        horizontal: "right",
      }}
      open={open}
      onClose={handleClose}
      message={
        <React.Fragment>
          <Typography sx={{ marginRight: 2 }}>
            We use cookies to improve your experience on our site.
          </Typography>
          <Typography component="div" variant="body2" sx={{ mt: 1 }}>
            <StyledLink href="/terms-and-conditions">
              Terms and Conditions
            </StyledLink>
          </Typography>
        </React.Fragment>
      }
      action={
        <>
          <Button
            color="secondary"
            size="small"
            sx={{
              color: "white",
              backgroundColor: "green",
              "&:hover": { backgroundColor: "darkgreen" },
              marginRight: "5px",
            }}
            onClick={() => handleConsent(true)}
          >
            ACCEPT
          </Button>
          <Button
            color="secondary"
            size="small"
            sx={{
              marginLeft: "5px",
              color: "white",
              backgroundColor: "green",
              "&:hover": { backgroundColor: "darkgreen" },
            }}
            onClick={() => handleConsent(false)}
          >
            DECLINE
          </Button>
          <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={handleClose}
          >
            <CloseIcon fontSize="small" />
          </IconButton>
        </>
      }
    />
  );
};

export default CookieConsent;
