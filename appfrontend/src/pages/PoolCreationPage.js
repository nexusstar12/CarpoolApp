import React, { useState } from "react";
import {
  TextField,
  Button,
  Checkbox,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Typography,
  Box,
  Grid,
  Paper,
  FormControlLabel,
} from "@mui/material";

export default function CreatePool({ isDriver, crews }) {
  const [poolName, setPoolName] = useState("");
  const [startAddress, setStartAddress] = useState({
    street: "",
    city: "",
    zip: "",
    state: "",
  });
  const [endAddress, setEndAddress] = useState({
    street: "",
    city: "",
    zip: "",
    state: "",
  });
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [isPublic, setIsPublic] = useState(true);
  const [selectedCrews, setSelectedCrews] = useState([]);

  const handleSubmit = (e) => {
    e.preventDefault();
    // Call API to save pool data or any other logic
    console.log("Pool created!");
  };

  // if (!isDriver) {
  //   return <Typography>You are not authorized to create a pool.</Typography>;
  // }

  return (
    <Box sx={{ flexGrow: 1, mt: 5 }}>
      <Grid container justifyContent="center">
        <Grid item xs={12} sm={10} md={8} lg={6}>
          <Paper elevation={3} sx={{ p: 3 }}>
            <Typography variant="h5" align="center" mb={3}>
              Create a New Pool
            </Typography>
            <Box component="form" onSubmit={handleSubmit}>
              <TextField
                fullWidth
                required
                label="Pool Name"
                value={poolName}
                onChange={(e) => setPoolName(e.target.value)}
                sx={{ mb: 2 }}
              />
              <Typography variant="h6" mt={2}>
                Start Address
              </Typography>
              {/* Similar TextFields for city, zip, state */}
              <Typography variant="h6" mt={2}>
                End Address
              </Typography>
              {/* Similar TextFields for city, zip, state */}
              <TextField
                fullWidth
                required
                label="Start Time"
                type="time"
                value={startTime}
                onChange={(e) => setStartTime(e.target.value)}
                sx={{ mb: 2 }}
              />
              <TextField
                fullWidth
                required
                label="End Time"
                type="time"
                value={endTime}
                onChange={(e) => setEndTime(e.target.value)}
                sx={{ mb: 2 }}
              />
              <FormControlLabel
                control={
                  <Checkbox
                    checked={isPublic}
                    onChange={(e) => setIsPublic(e.target.checked)}
                  />
                }
                label="Public Pool?"
                sx={{ mb: 2 }}
              />
              {!isPublic && (
                <FormControl fullWidth sx={{ mb: 2 }}>
                  <InputLabel id="crew-select-label">Select Crews</InputLabel>
                  <Select
                    labelId="crew-select-label"
                    multiple
                    value={selectedCrews}
                    onChange={(e) => setSelectedCrews(e.target.value)}
                  >
                    {crews.map((crew) => (
                      <MenuItem key={crew} value={crew}>
                        {crew}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>
              )}
              <Button variant="contained" color="primary" type="submit">
                Submit
              </Button>
            </Box>
          </Paper>
        </Grid>
      </Grid>
    </Box>
  );
}
