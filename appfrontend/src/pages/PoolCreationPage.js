import React, { useState } from "react";
import {
  Typography,
  TextField,
  Button,
  Box,
  Paper,
  Accordion,
  AccordionSummary,
  AccordionDetails,
  RadioGroup,
  FormControlLabel,
  Radio,
  Select,
  MenuItem,
} from "@mui/material";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { useContext } from "react";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { DateTimePicker } from "@mui/x-date-pickers/DateTimePicker";
import { DemoContainer } from "@mui/x-date-pickers/internals/demo";
import { UserContext } from "../App";
import { useNavigate } from "react-router-dom";
//import { Client } from '@googlemaps/google-maps-services-js';

import axiosInstance from "../config/axios.config";

export default function PostPool() {
  const userContext = useContext(UserContext);
  const history = useNavigate();
  const { profileId, jwtToken } = userContext.userInfo;
  const [selectedDate, setSelectedDate] = useState(null);
  const [privacy, setPrivacy] = useState("");
  const [crews, setCrews] = useState([]);
  const [selectedCrewId, setSelectedCrewId] = useState("");
  const [error, setError] = useState(null);
  const [nameError, setNameError] = useState(null);
  const [startStreetError, setStartStreetError] = useState(null);
  const [endStreetError, setEndStreetError] = useState(null);
  const [startCityError, setStartCityError] = useState(null);
  const [endCityError, setEndCityError] = useState(null);
  const [startStateError, setStartStateError] = useState(null);
  const [endStateError, setEndStateError] = useState(null);
  const [startZipError, setStartZipError] = useState(null);
  const [endZipError, setEndZipError] = useState(null);

  
  

  const handleSubmit = async (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const name = data.get("name");
    const startStreet = data.get("startStreet");
    const startCity = data.get("startCity");
    const startZip = data.get("startZip");
    const startState = data.get("startState");
    const endStreet = data.get("endStreet");
    const endZip = data.get("endZip");
    const endCity = data.get("endCity");
    const endState = data.get("endState");
    const formattedDate = selectedDate?.format("YYYY-MM-DDTHH:mm:ss");

    const requestBody = {
      name,
      startStreet,
      startCity,
      startZip,
      startState,
      startTime: formattedDate,
      endStreet,
      endZip,
      endCity,
      endState,
      crewId: selectedCrewId ? parseInt(selectedCrewId) : null,
      creatorId: profileId,
      privacy: privacy === "public" ? true : false,
    };

    console.log("requestBody", requestBody);

    try {
      const response = await axiosInstance.post(
        `/pool/createpool`,
        requestBody,
        {
          headers: {
            Authorization: `Bearer ${jwtToken}`,
          },
        }
      );
      console.log("response", response);
      if (response.status === 201) {
        history("/my-pools");
      }
    } catch (error) {
      console.log("error", error);
    }


    //field validations
    if(!validateName(name)){
      setNameError("Required");
    }
    else{
      setNameError(null);
    }
    if(!startStreet){
      setStartStreetError("Required");
    }
    else{
      setStartStreetError(null);
    }
    if(!startCity){
      setStartCityError("Required");
    }
    else{
      setStartCityError(null);
    }
    if(!validateZipCode(startZip)){
      setStartZipError("Required");
    }
    else{
      setStartZipError(null);
    }
    if(!validateState(startState)){
      setStartStateError("Required");
    }
    else{
      setStartStateError(null);
    }

    if(!endStreet){
      setEndStreetError("Required");
    }
    else{
      setEndStreetError(null);
    }
    if(!endCity){
      setEndCityError("Required");
    }
    else{
      setEndCityError(null);
    }
    if(!validateZipCode(endZip)){
      setEndZipError("Required");
    }
    else{
      setEndZipError(null);
    }

    if(!validateState(endState)){
      setEndStateError("Required");
    }
    else{
      setEndStateError(null);
    }

    return;


  
  };
  function validateName(name){
    let nameInput = name;
    const namePattern = /[a-zA-Z0-9_ ]/;
    return namePattern.test(nameInput);
  }
  /*const validateStreet = async (street) => {
    const client = new Client();
    try{
      const response = await client.geocode({
        params : {
          address : street,
        }
      });

      if(response.data.status === 'OK'){
        const formattedAddress = response.data.results[0].formatted_address;
      } else {
        setError('Invalid address');
      }
    } catch (error){
      return false;
    }

  }*/
  function validateState(state){
    let stateInput = state;
    const allStates = ["AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", 
    "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", 
    "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"];

    return(allStates.includes(stateInput, 0));

  }

  function validateZipCode(zipCode){
    let zipCodeInput = zipCode;
    if(zipCodeInput.length < 1 || zipCodeInput.length > 5){
      return false;
    }
    else{
      return true;
    }
  }


  const handlePrivacyChange = async (e) => {
    const privacy = e.target.value;
    console.log("privacy", privacy);
    setPrivacy(privacy);

    if (privacy === "private") {
      try {
        const { data } = await axiosInstance.get(`/crew/${profileId}`);
        setCrews(data);
      } catch (error) {}
    }
  };
  function helperFunction(){
    if(this.state.data.startState.trim().length > 0) return "OK";
    if (this.state.data.startState.replace(/[a-zA-Z0-9_ ]/g, '').length>0) return 'Only letters and numbers';
  };

  const handleSelectChange = async (e) => {
    const crew = e.target.value;
    setSelectedCrewId(crew);
  };


  return (
    <Box
      component="form"
      noValidate
      onSubmit={handleSubmit}
      sx={{
        mt: 5,
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
      }}
    >
      <Paper
        elevation={3}
        sx={{
          p: 3,
          width: "80%",
          maxWidth: 400,
          overflowY: "auto",
          maxHeight: "80vh",
        }}
      >
        <Typography variant="h5" align="center" mb={3}>
          Post a Pool
        </Typography>
        <Accordion>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            Pool Name
          </AccordionSummary>
          <AccordionDetails>
            <TextField fullWidth name="name" 
            label="Pool Name" 
            sx={{ mb: 2 }} 
            error = {!!nameError} 
            helperText = {nameError} />
          </AccordionDetails>
        </Accordion>

        {/* Enter start location section */}
        <Accordion>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            Start Location
          </AccordionSummary>
          <AccordionDetails>
            <TextField
              fullWidth
              name="startStreet"
              label="Street address"
              error = {!!startStreetError}
              helperText = {startStreetError}
              sx={{ mb: 2 }}
            />
            <TextField
              fullWidth
              name="startCity"
              label="Start city"
              error = {!!startCityError} 
              helperText = {startCityError}
              sx={{ mb: 2 }}
            />
            <TextField
              fullWidth
              name="startZip"
              label="Start zip code"
              error = {!!startZipError} 
              helperText = {startZipError}
              sx={{ mb: 2 }}
            />
            <TextField
              fullWidth
              name="startState"
              label="Start state"
              error = {!!startStateError} 
              helperText = {startStateError}
              sx={{ mb: 2 }}
            />
          </AccordionDetails>
        </Accordion>

        {/* Enter end location section */}
        <Accordion>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            End Location
          </AccordionSummary>
          <AccordionDetails>
            <TextField
              fullWidth
              name="endStreet"
              label="Street address"
              error = {!!endStreetError} 
              helperText = {endStreetError}
              sx={{ mb: 2 }}
            />
            <TextField
              fullWidth
              name="endCity"
              label="End city"
              error = {!!endCityError} 
              helperText = {endCityError}
              sx={{ mb: 2 }}
            />
            <TextField fullWidth name="endZip" label="End zip" sx={{ mb: 2 }} error = {!!endZipError} helperText = {endZipError}/>
            <TextField
              fullWidth
              name="endState"
              label="End state"
              error = {!!endStateError} 
              helperText = {endStateError}
              sx={{ mb: 2 }}
            />
          </AccordionDetails>
        </Accordion>

        {/* Start time/date section */}
        <Accordion>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            Date & Time
          </AccordionSummary>
          <AccordionDetails>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DemoContainer components={["DateTimePicker"]}>
                <DateTimePicker
                  required
                  error={"Wrong"}
                  label="Start date and time"
                  value={selectedDate}
                  onChange={(newValue) => setSelectedDate(newValue)}
                />
              </DemoContainer>
            </LocalizationProvider>
          </AccordionDetails>
        </Accordion>

        {/* Public or Private Pool section */}
        <Accordion>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            Privacy Settings
          </AccordionSummary>
          <AccordionDetails>
            <RadioGroup value={privacy} onChange={handlePrivacyChange}>
              <FormControlLabel
                value="public"
                control={<Radio />}
                name="publicPool"
                label="Public"
              />
              <FormControlLabel
                value="private"
                control={<Radio />}
                name="privatePool"
                label="Private"
              />
            </RadioGroup>
            {privacy === "private" && (
              <Select
                fullWidth
                value={selectedCrewId}
                onChange={handleSelectChange}
                displayEmpty // This prop allows us to display an empty item
              >
                <MenuItem value="">Please select a crew</MenuItem>
                {crews.length &&
                  crews.map((crew) => (
                    <MenuItem key={crew.crewId} value={crew.crewId}>
                      {crew.description}
                    </MenuItem>
                  ))}
              </Select>
            )}
          </AccordionDetails>
        </Accordion>

        <Button
          variant="contained"
          color="primary"
          type="submit"
          sx={{ mt: 2 }}
        >
          Submit
        </Button>
      </Paper>
    </Box>
  );
}
