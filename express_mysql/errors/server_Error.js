class server_Error extends Error {
    constructor(msg, statusCode) {
        super(msg);
        this.msg = msg;
        this.statusCode = statusCode;
        this.error = `${statusCode}`.startsWith('4') ? 'fail' : 'error';
        this.isOperational = true;

        Error.captureStackTrace(this, this.constructor);
    }
}

export default server_Error;