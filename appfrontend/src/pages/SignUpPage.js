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
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import InputLabel from "@mui/material/InputLabel";
import Alert from "@mui/material/Alert";

const defaultTheme = createTheme();

export default function SignUp() {
  const history = useNavigate();
  const [error, setError] = useState(null);
  const [registerDriver, setRegisterDriver] = useState(false);
  const [licenseError, setLicenseError] = useState("");
  const licenseRegex = /^[A-Za-z0-9]{1,20}$/; // 20 characters regex validation for the driver's license

  const handleLicenseChange = (e) => {
    const value = e.target.value;
    if (!licenseRegex.test(value)) {
      setLicenseError(
        "Please enter valid driver license (Max 20 chars, letters & numbers only)"
      );
    } else {
      setLicenseError("");
    }
  };

  const [isValid, setIsValid] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const firstName = data.get("firstName");
    const lastName = data.get("lastName");
    const phoneNumber = data.get("phoneNumber");
    const email = data.get("email");
    const password = data.get("password");
    const requestBody = { firstName, lastName, phoneNumber, email, password };

    if (!isPhoneNumberValid(phoneNumber)) {
      setError("Invalid phone number");
      return;
    }

    try {
      const response = await axiosInstance.post("/api/signup/", requestBody);

      if (response.status === 201) {
        history("/signin");
      }
    } catch (error) {
      setError(error.response.data.message);
    }
  };

  return (
    <ThemeProvider theme={defaultTheme}>
      <Grid container component="main" sx={{ height: "100vh" }}>
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
                        sx={{ width: "70%" }} // Adjust this value as per your requirement for size
                        name="driverLicense"
                        label="Driver's License"
                        onChange={handleLicenseChange}
                        error={!!licenseError}
                        helperText={licenseError}
                      />
                    </Grid>
                    <Grid item xs={12}>
                      <FormControlLabel
                        control={<Checkbox color="primary" />}
                        label="FasTrak verification status"
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
