function handleClick() {
    alert("I've been clicked!")
}

function Buttion() {
    return (
        <button onClick={handleClick}>Click me!</button>
    )
}

function MyApp() {
    return (
        <div>
            <h1>Welcome to my app</h1>
            <Buttion />
        </div>
    );
}