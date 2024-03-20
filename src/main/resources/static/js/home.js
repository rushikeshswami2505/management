document.getElementById("inwardDozen").addEventListener("input", function() {
        var dozenValue = parseInt(document.getElementById("inwardDozen").value);
        var pieceValue = dozenValue * 12;
        document.getElementById("inwardPiece").value = pieceValue;
});
document.getElementById("inwardPiece").addEventListener("input", function() {
        var dozenValue = parseInt(document.getElementById("inwardPiece").value);
        var pieceValue = Math.round(dozenValue/12 * 10 ** 2) / 10 ** 2;
        document.getElementById("inwardDozen").value = pieceValue;
});