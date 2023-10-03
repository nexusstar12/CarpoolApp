import { useState } from "react";
import IconButton from "@mui/material/IconButton";
import SearchIcon from "@mui/icons-material/Search";
import TextField from "@mui/material/TextField";

export default function SearchBar() {
    const data = [
        "San Francisco",
        "Paris",
        "London",
        "New York",
        "Tokyo",
        "Berlin",
        "Buenos Aires",
        "Cairo",
        "Canberra",
        "Rio de Janeiro",
        "Dublin"
    ];

    const [searchQuery, setSearchQuery] = useState("");
    const [dataFiltered, setDataFiltered] = useState([]);
    const [isSearchPerformed, setIsSearchPerformed] = useState(false);

    const filterData = (query, data) => {
        if (!query) {
            return [];
        } else {
            return data.filter((d) => d.toLowerCase().includes(query));
        }
    };

    const handleSearch = (e) => {
        const query = e.target.value.toLowerCase();
        const filteredData = filterData(query, data);
        setSearchQuery(query);
        setDataFiltered(filteredData);
        setIsSearchPerformed(true); // Set search performed to true
    };

    return (
        <div
            style={{
                display: "flex",
                alignSelf: "center",
                justifyContent: "center",
                flexDirection: "column",
                padding: 20
            }}
        >
            <form>
                <TextField
                    id="search-bar"
                    className="text"
                    onInput={handleSearch}
                    label="Enter a city name"
                    variant="outlined"
                    placeholder="Search..."
                    size="small"
                />
                <IconButton type="submit" aria-label="search">
                    <SearchIcon style={{ fill: "white" }} />
                </IconButton>
            </form>
            {/* <div style={{ padding: 3 }}>
                {isSearchPerformed ? (
                    dataFiltered.length > 0 ? (
                        dataFiltered.map((d, index) => (
                            <div
                                className="text"
                                style={{
                                    padding: 5,
                                    justifyContent: "center",
                                    fontSize: 20,
                                    color: "blue",
                                    margin: 1,
                                    width: "250px",
                                    borderColor: "green",
                                    borderWidth: "10px",
                                    textAlign: "center",
                                    backgroundColor: index % 2 === 0 ? "lightgray" : "white"
                                }}
                                key={index}
                            >
                                {d}
                            </div>
                        ))
                    ) : (
                        <p>No matching cities found.</p>
                    )
                ) : (
                    <p>Enter a city name to search.</p>
                )}
            </div> */}
        </div>
    );
}
