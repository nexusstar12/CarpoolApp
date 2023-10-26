import React from "react";
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

import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { DateTimePicker } from "@mui/x-date-pickers/DateTimePicker";
import { DemoContainer } from "@mui/x-date-pickers/internals/demo";

export default function PostPool() {
  const [selectedDate, setSelectedDate] = React.useState(null);
  const [type, setType] = React.useState("");
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
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
      type,
    };
    console.log("requestBody", requestBody);
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
      <Paper elevation={3} sx={{ p: 3, width: "80%", maxWidth: 400 }}>
        <Typography variant="h5" align="center" mb={3}>
          PoolApp
        </Typography>
        <Typography variant="h6" align="center" mb={3}>
          Post a Pool
        </Typography>

        {/* Enter start location section */}
        <Accordion>
          <AccordionSummary expandIcon={<ExpandMoreIcon />}>
            Enter start location
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
            Enter end location
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
            Start time/date
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
            Public or Private Pool?
          </AccordionSummary>
          <AccordionDetails>
            <RadioGroup value={type} onChange={(e) => setType(e.target.value)}>
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
              {/* Add more MenuItems if needed */}
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
