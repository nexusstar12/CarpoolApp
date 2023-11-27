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
import Modal from "@mui/material/Modal";
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
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const [firstNameError, setFirstNameError] = useState(null);
  const [lastNameError, setLastNameError] = useState(null);
  const [firstNameCharError, setFirstNameCharError] = useState(null);
  const [lastNameCharError, setLastNameCharError] = useState(null);

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
    if (firstName.length > 50) {
      setFirstNameError("Enter a name less than 51 characters long.");
      return;
    } else {
      setFirstNameError(null);
    }
    if (lastName.length > 50) {
      setLastNameError("Enter a name less than 51 characters long.");
      return;
    } else {
      setLastNameError(null);
    }
    const firstNameRegex = /^[a-zA-Z.-]+$/;
    if (!firstNameRegex.test(firstName)) {
      setFirstNameCharError(
        "Enter a name consisting only of letters, hyphens, or periods."
      );
      return;
    } else {
      setFirstNameCharError(null);
    }
    const lastNameRegex = /^[a-zA-Z.-]+$/;
    if (!lastNameRegex.test(lastName)) {
      setLastNameCharError(
        "Enter a name consisting only of letters, hyphens, or periods."
      );
      return;
    } else {
      setLastNameCharError(null);
    }
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
      const response = await axiosInstance.post("/signup", requestBody);

      if (response.status === 201) {
        setShowSuccessModal(true);
      }
    } catch (error) {
      setError(error.response.data.message);
    }
  };

  const SuccessModal = () => {
    const handleRedirect = () => {
      setShowSuccessModal(false);
      history("/signin");
    };

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
            Signup Successful!
          </Typography>
          <Typography id="success-modal-description" sx={{ mt: 2 }}>
            Your account has been created. You can now sign in.
          </Typography>
          <Button onClick={handleRedirect}>Go to Sign In</Button>
        </Box>
      </Modal>
    );
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
          xs={12}
          sm={12}
          md={6}
          lg={6}
          component={Paper}
          elevation={6}
          square
          sx={{
            height: "90vh",
            overflow: "auto",
            display: "flex",
            flexDirection: "column",
          }}
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
                    error={!!firstNameCharError || !!firstNameError}
                    helperText={firstNameCharError || firstNameError || ""}
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
                    error={!!lastNameCharError || !!lastNameError}
                    helperText={lastNameCharError || lastNameError || ""}
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
                        name="driversLicense"
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
              <Typography variant="body2" color="textSecondary" align="center">
                By signing up, you agree to Pool's{" "}
                <Link href="/terms-and-conditions">terms and conditions</Link>.
              </Typography>
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
          sm={0}
          md={6}
          lg={6}
          elevation={6}
          sx={{
            backgroundImage: "url(signup_poolapp.jpg)",
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
      </Grid>
      <SuccessModal />
    </ThemeProvider>
  );
}
