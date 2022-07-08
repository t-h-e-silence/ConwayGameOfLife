import axios from 'axios';

const GAME_API_BASE_URL = "http://localhost:8080/my-app/";

class GameService {

    startGame(grid) {
        return axios.post(GAME_API_BASE_URL + "newGame", grid);
    }

    newGeneration(grid) {
        return axios.post(GAME_API_BASE_URL + "newGeneration", grid);
    }

    gameOver(grid) {
        return axios.post(GAME_API_BASE_URL + "gameOver", grid);
    }

}

export default new GameService();