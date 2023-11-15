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

import axiosInstance from "../config/axios.config";

export default function PostPool() {
  const userContext = useContext(UserContext);
  const history = useNavigate();
  const { profileId, jwtToken } = userContext.userInfo;
  const [selectedDate, setSelectedDate] = useState(null);
  const [privacy, setPrivacy] = useState("");
  const [crews, setCrews] = useState([]);
  const [selectedCrewId, setSelectedCrewId] = useState("");

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
  };

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
            <TextField fullWidth name="name" label="Pool Name" sx={{ mb: 2 }} />
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
              sx={{ mb: 2 }}
            />
            <TextField
              fullWidth
              name="startCity"
              label="Start city"
              sx={{ mb: 2 }}
            />
            <TextField
              fullWidth
              name="startZip"
              label="Start zip code"
              sx={{ mb: 2 }}
            />
            <TextField
              fullWidth
              name="startState"
              label="Start state"
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
              sx={{ mb: 2 }}
            />
            <TextField
              fullWidth
              name="endCity"
              label="End city"
              sx={{ mb: 2 }}
            />
            <TextField fullWidth name="endZip" label="End zip" sx={{ mb: 2 }} />
            <TextField
              fullWidth
              name="endState"
              label="End state"
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
