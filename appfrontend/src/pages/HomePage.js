import SearchBar from "../components/SearchBar";
import React, { useState } from "react";
import axiosInstance from "../config/axios.config";
import { SearchResult } from "../components/SearchResult";

function HomePage() {
  const [search, setSearch] = useState("");
  const [result, setResult] = useState([]);

  const handleSearchSubmit = async ({ searchQuery, filterOption }) => {
    setSearch({ searchQuery, filterOption });

    const data = await axiosInstance.get(
      `/api/searchbar?filter=${filterOption}&value=${searchQuery}`
    );
    setResult(data.data);
  };

  return (
    <div className="search-container">
      <SearchBar onSearch={handleSearchSubmit} />
      <div className="search-results-container">
        <SearchResult
          className="search-result"
          result={result}
          itemsPerPage={10}
        />
      </div>
    </div>
  );
}

export default HomePage;
