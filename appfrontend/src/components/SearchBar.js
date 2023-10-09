import React, { useState, useEffect } from "react";
import TextField from "@mui/material/TextField";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import Popup from "reactjs-popup";

const SearchBar = ({ onSearch, onFilterChange }) => {
  const [searchQuery, setSearchQuery] = useState("");
  const [filterOption, setFilterOption] = useState("");
  const [isPopupOpen, setIsPopupOpen] = useState(false);

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
  };

  const closePopup = () => {
    setIsPopupOpen(false);
  };

  return (
    <div style={{ display: "flex", alignItems: "center" }}>
      <Popup
        open={isPopupOpen}
        onClose={closePopup}
        position="bottom center"
        arrow={false}
      >
        Please select an option before searching.
      </Popup>
      <TextField
        label="Search"
        variant="outlined"
        value={searchQuery}
        onChange={handleSearchInputChange}
        onKeyDown={handleKeyDown}
        style={{ marginRight: "10px", width: "500px" }}
      />

      <Select
        label="Filter"
        variant="outlined"
        value={filterOption}
        onChange={handleFilterChange}
        style={{ minWidth: "120px" }}
      >
        <MenuItem value="origin">origin</MenuItem>
        <MenuItem value="destination">destination</MenuItem>
        <MenuItem value="city name">city name</MenuItem>
        <MenuItem value="name">user name</MenuItem>
      </Select>
    </div>
  );
};

export default SearchBar;
