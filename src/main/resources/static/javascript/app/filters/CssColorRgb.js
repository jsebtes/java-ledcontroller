/**
 * Filtre pour affichage d'une ColorRgb en CSS.
 */
export default () => (token) => {
    if (typeof token === 'undefined' || token === null || token === '') {
        return '';
    }
    return 'rgb(' + token.red + ', ' + token.green + ', ' + token.blue + ')';
};

