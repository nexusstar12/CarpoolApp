import React, { useState } from "react";
import TextField from "@mui/material/TextField";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";

const SearchBar = ({ onSearch, onFilterChange }) => {
  const [searchQuery, setSearchQuery] = useState("");
  const [filterOption, setFilterOption] = useState("");

  const handleSearchInputChange = (event) => {
    setSearchQuery(event.target.value);
  };

  const handleKeyDown = (event) => {
    onSearch(searchQuery);
  };

  const handleFilterChange = (event) => {
    setFilterOption(event.target.value);
    // onFilterChange(event.target.value);
  };

  return (
    <div style={{ display: "flex", alignItems: "center" }}>
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
        <MenuItem value="option1">origin</MenuItem>
        <MenuItem value="option2">destination</MenuItem>
        <MenuItem value="option3">city name</MenuItem>
      </Select>
    </div>
  );
};

export default SearchBar;
