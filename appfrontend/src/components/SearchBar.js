import React, { useState, useEffect } from "react";
import TextField from "@mui/material/TextField";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import Popup from "reactjs-popup";

const SearchBar = ({ onSearch, onFilterChange }) => {
  const [searchQuery, setSearchQuery] = useState("");
  const [filterOption, setFilterOption] = useState("");
  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const [searchPlaceholder, setSearchPlaceholder] = useState("Search");

  const handleSearchInputChange = (event) => {
    setSearchQuery(event.target.value);
  };

  const handleKeyDown = (event) => {
    if (event.key === "Enter") {
      if (!filterOption) {
        setIsPopupOpen(true);
      } else {
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

  const closePopup = () => {
    setIsPopupOpen(false);
  };

  return (
    <div style={{ marginTop: "100px" }}>
      <h2>Find a carpool in your area. </h2>
      <div
        className="search-bar"
        style={{ display: "flex", alignItems: "center", marginTop: "10px" }}
      >
        <Popup
          open={isPopupOpen}
          onClose={closePopup}
          position="bottom center"
          arrow={false}
        >
          Please select an option before searching.
        </Popup>
        <TextField
          label={searchPlaceholder}
          variant="outlined"
          value={searchQuery}
          onChange={handleSearchInputChange}
          onKeyDown={handleKeyDown}
          style={{ marginRight: "10px", width: "400px" }}
        />
        <Select
          label="Filter"
          variant="outlined"
          value={filterOption}
          onChange={handleFilterChange}
          style={{ minWidth: "120px" }}
        >
          <MenuItem value="startZip">Starting Zipcode</MenuItem>
          <MenuItem value="endZip">Ending Zipcode</MenuItem>
          <MenuItem value="city">City</MenuItem>
        </Select>
      </div>
    </div>
  );
};

export default SearchBar;
