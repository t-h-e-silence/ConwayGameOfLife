import React, {useEffect, useState} from 'react';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import GameService from "../service/GameService";
import {render} from "react-dom";

function GameOfLifeUI() {

    const [grid, setState] = useState({
        rows: 25,
        cols: 25,
        amountOfAliveCells: 200,
        amountOfActiveThreads: 5,
        amountOfAlLCells: 500,
        cells: []
    });

    const updateField = e => {
        setState({
            ...grid,
            [e.target.name]: e.target.value
        });
    };

    const generateGrid = () => {
        GameService.startGame(grid).then(res => {
            console.log(res.data);
            setState({
                ...grid,
                ["cells"]: res.data.cells,
                ["amountOfAlLCells"]: res.data.amountOfAlLCells
            });
        });

    }

    useEffect(function updateRender() {
        if (typeof grid.cells !== 'undefined') {
            if (grid.cells.length !== 0) {
                render(Board(grid), document.getElementById('gridContainer'));
            }
        }
    });

    useEffect(function gameOver() {
        if (typeof grid.cells !== 'undefined') {
            if (grid.cells.length !== 0) {
                if (grid.amountOfAliveCells == 0 || grid.amountOfAliveCells == grid.amountOfAlLCells) {
                    endGame();
                    render(<p> GAME OVER </p>, document.getElementById('gameStatus'));
                }
            }
        }
    });

    useEffect(function newGenerationRunner() {
        if (typeof grid.cells !== 'undefined') {
            if (grid.cells.length !== 0) {
               newGeneration();
            }
        }
    });

    const newGeneration = () => {
        GameService.newGeneration(grid).then(res => {
            console.log(res.data);
            setState({
                ...grid,
                ["cells"]: res.data.cells,
                ["amountOfAliveCells"]: res.data.amountOfAliveCells
            });
        });
    }

    const endGame = () => {
        GameService.gameOver(grid).then(res => {
            console.log(res.data);
        });
    }

    const Board = (grid) => {
        let rows = [];
        for (let y = 0; y < grid.rows; y++) {
            const cells = [];
            for (let x = 0; x < grid.cols; x++) {
                if (grid.cells[y][x] === 1) {
                    cells.push(<td className="aliveGridCell"/>);
                } else cells.push(<td className="deadGridCell"/>);
            }
            rows.push(<tr>{cells}</tr>);
        }
        return <table className="text-center">
            <tbody>{rows}</tbody>
        </table>;
    }

    return (
        <div>
            <Form>
                <Row>
                    <Col>
                        <Form.Control placeholder="Rows number" value={grid.rows} name="rows"
                                      onChange={updateField}/>
                    </Col>
                    <Col>
                        <Form.Control placeholder="Columns number" value={grid.cols} name="cols"
                                      onChange={updateField}/>
                    </Col>
                    <Col>
                        <Form.Control placeholder="Active cells:" value={grid.amountOfAliveCells}
                                      name="amountOfAliveCells"
                                      onChange={updateField}/>
                    </Col>
                    <Col>
                        <Form.Control placeholder="Threads" value={grid.amountOfActiveThreads}
                                      name="amountOfActiveThreads"
                                      onChange={updateField}/>
                    </Col>
                </Row>
                <Button className="btn btn-group-sm navbar-text" onClick={generateGrid}>Generate new grid</Button>
                <Button className="btn btn-group-sm navbar-text" onClick={endGame}>Stop game</Button>
            </Form>
            <div id="gridContainer" className="center-layout"/>
            <div id="gameStatus" className="text-info center-layout"></div>
        </div>
    );
}

export default GameOfLifeUI;
