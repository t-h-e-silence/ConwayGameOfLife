import React, {Component} from 'react';

class HeaderComponent extends Component {

    constructor() {
        super();
    }

    render() {
        return (
            <header>
                <p className="navbar-expand-lg navbar-light bg-primary">
                    <h1><p className="text-light text-center"> GAME OF LIFE </p></h1>
                </p>
            </header>
        )
    }
}

export default HeaderComponent;