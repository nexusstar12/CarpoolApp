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

export default function PostPool() {
  const userContext = useContext(UserContext);
  const [selectedDate, setSelectedDate] = useState(null);
  const [privacy, setPrivacy] = useState("");

  console.log("userContext", userContext.userInfo);
  const handleSubmit = (event) => {
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
    const formattedDate = selectedDate?.format("YYYY-MM-DD HH:mm:ss");

    const requestBody = {
      name,
      start: {
        startStreet,
        startCity,
        startZip,
        startState,
        date: formattedDate,
      },
      end: {
        endStreet,
        endZip,
        endCity,
        endState,
      },
      privacy,
    };
    console.log("requestBody", requestBody);
  };
  // if (!userContext?.userInfo?.isDrive) {
  //   return (
  //     <Box
  //       sx={{
  //         mt: 5,
  //         display: "flex",
  //         flexDirection: "column",
  //         alignItems: "center",
  //       }}
  //     >
  //       <Typography variant="h6" color="error">
  //         Only drivers can create a pool.
  //       </Typography>
  //     </Box>
  //   );
  // }
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
          PoolApp
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
              label="startStreet"
              sx={{ mb: 2 }}
            />
            <TextField
              fullWidth
              name="startCity"
              label="startCity"
              sx={{ mb: 2 }}
            />
            <TextField
              fullWidth
              name="startZip"
              label="startZip"
              sx={{ mb: 2 }}
            />
            <TextField
              fullWidth
              name="startState"
              label="startState"
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
              label="endStreet"
              sx={{ mb: 2 }}
            />
            <TextField
              fullWidth
              name="endCity"
              label="endCity"
              sx={{ mb: 2 }}
            />
            <TextField fullWidth name="endZip" label="endZip" sx={{ mb: 2 }} />
            <TextField
              fullWidth
              name="endState"
              label="endState"
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
                  label="Basic date time picker"
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
            <RadioGroup
              value={privacy}
              onChange={(e) => setPrivacy(e.target.value)}
            >
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
            <Select fullWidth value="Private Pools">
              <MenuItem value="Private Pools">Private Pools</MenuItem>
            </Select>
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
