import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Link from "@mui/material/Link";
import Paper from "@mui/material/Paper";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import axiosInstance from "../config/axios.config";
import { useNavigate } from "react-router-dom";
import React, { useState } from "react";
import { isPhoneNumberValid } from "../utilities/phoneNumberValidation";

const defaultTheme = createTheme();

export default function SignUp() {
  const history = useNavigate();
  const [error, setError] = useState(null);
  const [phoneError, setPhoneError] = useState(null);
  const [emailError, setEmailError] = useState(null);
  const [licenseError, setLicenseError] = useState(null);

  const [registerDriver, setRegisterDriver] = useState(false);
  const licenseRegex = /^[a-zA-Z0-9]{1,20}$/;
  const emailRegex = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;

  const handleSubmit = async (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const firstName = data.get("firstName");
    const lastName = data.get("lastName");
    const phoneNumber = data.get("phoneNumber");
    const driversLicense = data.get("driversLicense");
    const email = data.get("email");
    const password = data.get("password");
    const fasTrakVerification = data.get("fasTrakVerification");

    const requestBody = {
      firstName,
      lastName,
      phoneNumber,
      email,
      password,
      fasTrakVerification,
      driversLicense,
      role: driversLicense ? "driver" : "passenger",
    };

    if (!isPhoneNumberValid(phoneNumber)) {
      setPhoneError("Invalid phone number");
      return;
    } else {
      setPhoneError(false);
    }

    if (driversLicense) {
      if (!licenseRegex.test(driversLicense)) {
        setLicenseError("Invalid license");
        return;
      } else {
        setLicenseError(false);
      }
    }

    if (!email || !emailRegex.test(email)) {
      setEmailError("Invalid email");
      return;
    } else {
      setEmailError(false);
    }

    try {
      console.log("requestBody", requestBody);
      const response = await axiosInstance.post("/signup", requestBody);

      if (response.status === 201) {
        history("/signin");
      }
    } catch (error) {
      setError(error.response.data.message);
    }
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Grid container component="main" sx={{ minHeight: "100vh" }}>
        <CssBaseline />
        <Grid
          item
          xs={12}
          sm={10}
          md={5}
          component={Paper}
          elevation={6}
          square
        >
          <Box
            sx={{
              my: registerDriver ? 4 : 2,
              mx: 4,
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
            }}
          >
            <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
              <LockOutlinedIcon />
            </Avatar>
            <Typography component="h1" variant="h5">
              Sign up
            </Typography>
            <Box
              component="form"
              noValidate
              onSubmit={handleSubmit}
              sx={{ mt: 3 }}
            >
              <Grid container spacing={2}>
                <Grid item xs={12} sm={6}>
                  <TextField
                    autoComplete="given-name"
                    name="firstName"
                    required
                    fullWidth
                    id="firstName"
                    label="First Name"
                    autoFocus
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    required
                    fullWidth
                    id="lastName"
                    label="Last Name"
                    name="lastName"
                    autoComplete="family-name"
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    required
                    fullWidth
                    id="phoneNumber"
                    label="Phone Number"
                    name="phoneNumber"
                    autoComplete="phoneNumber"
                    error={!!phoneError}
                    helperText={phoneError}
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    required
                    fullWidth
                    id="email"
                    label="Email Address"
                    name="email"
                    autoComplete="email"
                    error={!!emailError}
                    helperText={emailError}
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    required
                    fullWidth
                    name="password"
                    label="Password"
                    type="password"
                    id="password"
                    autoComplete="new-password"
                  />
                </Grid>
                <Grid item xs={12}>
                  <FormControlLabel
                    control={
                      <Checkbox
                        color="primary"
                        onChange={(e) => setRegisterDriver(e.target.checked)}
                      />
                    }
                    label="Would you like to register as a driver?"
                  />
                </Grid>
                {registerDriver && (
                  <>
                    <Grid
                      item
                      xs={12}
                      sx={{ display: "flex", justifyContent: "center" }}
                    >
                      <TextField
                        required
                        sx={{ width: "70%" }}
                        name="driverLicense"
                        label="Driver's License"
                        error={!!licenseError}
                        helperText={licenseError}
                      />
                    </Grid>

                    <Grid item xs={12}>
                      <FormControlLabel
                        control={<Checkbox color="primary" />}
                        value={true}
                        label="I have an active FasTrak account"
                        name="fasTrakVerification"
                      />
                    </Grid>
                  </>
                )}
              </Grid>
              <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
              >
                Sign Up
              </Button>
              <Typography color="error" variant="body2">
                {error}
              </Typography>
              <Grid container justifyContent="flex-end">
                <Grid item>
                  <Link href="/signin" variant="body2">
                    Already have an account? Sign in
                  </Link>
                </Grid>
              </Grid>
            </Box>
          </Box>
        </Grid>
        <Grid
          item
          xs={false}
          sm={4}
          md={7}
          sx={{
            backgroundImage: "url(signup_poolapp.jpg)",
            backgroundRepeat: "no-repeat",
            backgroundColor: (t) =>
              t.palette.mode === "light"
                ? t.palette.grey[50]
                : t.palette.grey[900],
            backgroundSize: "cover",
            backgroundPosition: "center",
          }}
        />
      </Grid>
    </ThemeProvider>
  );
}
