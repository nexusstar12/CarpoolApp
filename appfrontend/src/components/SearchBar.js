import React, { useState, useEffect } from "react";
import TextField from "@mui/material/TextField";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import Popup from "reactjs-popup";
import SearchIcon from "@mui/icons-material/Search";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";

const SearchBar = ({ onSearch, onFilterChange }) => {
  const [searchQuery, setSearchQuery] = useState("");
  const [filterOption, setFilterOption] = useState("");
  const [error, setError] = useState(false);
  const [searchPlaceholder, setSearchPlaceholder] = useState("Search");
  const [hasSearched, setHasSearched] = useState(false);

  const handleSearchInputChange = (event) => {
    setSearchQuery(event.target.value);
  };

  const handleKeyDown = (event) => {
    if (event.key === "Enter") {
      if (!filterOption) {
        setError(true);
      } else {
        setError(false);
        setHasSearched(true);
        onSearch({ searchQuery, filterOption });
      }
    }
  };

  const handleFilterChange = (event) => {
    setFilterOption(event.target.value);
    if (event.target.value === "startZip") {
      setSearchPlaceholder("Enter a start location zip code");
    } else if (event.target.value === "endZip") {
      setSearchPlaceholder("Enter an end location zip code");
    } else if (event.target.value === "city") {
      setSearchPlaceholder("Enter a city");
    }
  };

  const handleClick = () => {
    if (!filterOption) {
      setError(true);
    } else {
      setError(false);
      setHasSearched(true);
      onSearch({ searchQuery, filterOption });
    }
  };

  return (
    <div
      style={{
        marginTop: hasSearched ? "0px" : "100px",
        textAlign: "center",
        transition: "margin-top 0.5s",
      }}
    >
      <h2>Find a carpool in your area. </h2>
      <div
        className="search-bar"
        style={{ display: "flex", alignItems: "center", marginTop: "10px" }}
      >
        <TextField
          label={searchPlaceholder}
          variant="outlined"
          value={searchQuery}
          onChange={handleSearchInputChange}
          onKeyDown={handleKeyDown}
          style={{ marginRight: "10px", width: "300px" }}
        />
        <Select
          label="Filter"
          variant="outlined"
          value={filterOption}
          onChange={handleFilterChange}
          displayEmpty
          style={{ minWidth: "120px" }}
        >
          <MenuItem value="" disabled>
            Select
          </MenuItem>
          <MenuItem value="startZip">Starting Zipcode</MenuItem>
          <MenuItem value="endZip">Ending Zipcode</MenuItem>
          <MenuItem value="city">City</MenuItem>
        </Select>
      </div>
      <Button
        variant="contained"
        color="primary"
        endIcon={<SearchIcon />}
        onClick={handleClick}
        style={{ marginTop: "10px", marginBottom: "10px" }}
      >
        Search
      </Button>
      {error && (
        <Typography color="error" variant="body2">
          {error && "Please select an option before searching."}
        </Typography>
      )}
    </div>
  );
};

export default SearchBar;
