import axios from "axios";

import SearchBar from "../components/SearchBar";
import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import Bar from "../components/Bar";
import React, { useState, useEffect } from "react";

function HomePage() {
  const [search, setSearch] = useState("");

  const handleSearchSubmit = ({ searchQuery, filterOption }) => {
    setSearch({ searchQuery, filterOption });
  };

  return (
    <div className="search-container">
      <SearchBar onSearch={handleSearchSubmit} />
    </div>
  );
}

export default HomePage;
