/*global $, moment*/
/*jslint devel: true, plusplus: true, continue: true*/
/*eslint-env browser*/
/*eslint no-console: off*/

var ultimoId = 2;

function esDeEdadLegalParaTrabajar(fechaDeNacimiento) {
    'use strict';
    
    var hoy, cumpleMayoriaEdad, cumpleJubilacion;
    
    hoy = moment().startOf('day');
    fechaDeNacimiento = moment(fechaDeNacimiento).startOf('day');
    
    cumpleMayoriaEdad = moment(fechaDeNacimiento).add(18, 'year');
    cumpleJubilacion = moment(fechaDeNacimiento).add(65, 'year');
    
    return hoy >= cumpleMayoriaEdad && hoy < cumpleJubilacion;
}

/*
function esDeEdadLegalParaTrabajar(fechaDeNacimiento) {
    'use strict';
    
    var hoy = new Date(), cumpleMayoriaEdad, cumpleJubilacion;
    
    hoy = new Date(hoy.getFullYear(), hoy.getMonth(), hoy.getDate(), 1, 0, 0);
    
    cumpleMayoriaEdad = new Date(fechaDeNacimiento);
    cumpleMayoriaEdad.setFullYear(fechaDeNacimiento.getFullYear() + 18);
    
    cumpleJubilacion = new Date(fechaDeNacimiento);
    cumpleJubilacion.setFullYear(fechaDeNacimiento.getFullYear() + 65);
    
    return hoy >= cumpleMayoriaEdad && hoy < cumpleJubilacion;
}
*/
function esDniExtranjero(dni) {
    'use strict';
    
    return isNaN(parseInt(dni[0], 10));
}

function esDniValido(dni) {
    'use strict';
    var numero, letra, letras;
    
    letras = 'TRWAGMYFPDXBNJZSQVHLCKE';
    
    numero = dni.substring(0, dni.length - 1);
    
    letra = dni[8];
    
    numero = numero.replace('X', '0').replace('Y', '1').replace('Z', '2');
    
    return letra === letras[numero % 23];
}

function filaTablaAFormulario(id) {
    'use strict';

    //btn.parentNode.parentNode.columns[0].innerText   //$btn.parents('tr').find('td')[0]
    var datos, form;

    datos = $('#id' + id)[0].dataset;
    form = $('#formulario')[0];
    //$('#nombre').val($tr.data('nombre'));
    //TODO: Verificar que el id se recoge correctamente en otros navegadores
    form.id.value = id;
    form.nombre.value = datos.nombre;
    form.apellidos.value = datos.apellidos;
    form.email.value = datos.email;
    form.dni.value = datos.dni;
    form['fecha-de-nacimiento'].value = datos.fechaDeNacimiento;
    form.nacionalidad.value = datos.nacionalidad;


    //$tr.data('nombre');
    //$('#id' + id).data('apellidos');
}


function modificarBoton(texto, tipo) {
    'use strict';

    $('#btn-enviar')[0].className = 'btn btn-' + tipo;
    $('#btn-enviar').text(texto);
}

/*
function valoresFalsos() {
    'use strict';
    
    var form = document.forms[0];
    
    form.id.value = 5;
    form.nombre.value = 'Javier';
    form.apellidos.value = 'Lete García';
    form.email.value = 'javierlete@email.net';
    form.dni.value = '12345678Z';
    form['fecha-de-nacimiento'].value = '2000-01-02';
    form.nacionalidad.value = 1;
}
*/

function limpiarFormulario() {
    'use strict';

    //var form = document.forms[0];

    //$('#nombre').val('');
    //form.apellidos.value = '';

    //$('input:not([type=hidden])').val('');
    $('input').val('');
    $('select').val('0');

    //form.reset();
}

function extraerCamposUrl(url) {
    'use strict';

    var datos = [];

    $(url.split('?')[1].split('&')).each(function () {
        var datoValor = this.split('=');
        datos[datoValor[0]] = datoValor[1];
    });

    return datos;
}
function anadir() {
    'use strict';

    limpiarFormulario();

    //TODO: Quitar esta función cuando esté terminado
    //valoresFalsos();
    
    modificarBoton('Añadir', 'primary');
}

function editar(id) {
    'use strict';

    filaTablaAFormulario(id);

    modificarBoton('Editar', 'primary');
}

function borrar(id) {
    'use strict';

    filaTablaAFormulario(id);

    modificarBoton('Borrar', 'danger');
}

function gestionEventoClickHrefOpcion(e, enlace) {
    'use strict';
    
    e.preventDefault();

    var href = extraerCamposUrl(enlace.href);

    console.log(href.opcion, href.id);

    $('#btn-enviar').val(href.opcion);

    switch (href.opcion) {
    case 'anadir':
        anadir();
        break;
    case 'editar':
        editar(href.id);
        break;
    case 'borrar':
        borrar(href.id);
        break;
    }

    $('#detalle').show();
}




function formularioAFilaTabla(id) {
    'use strict';

    var celdas, datos, form, fila, f, nac;

    fila = $('#id' + id)[0];
    datos = fila.dataset;
    form = $('#formulario')[0];
    celdas = fila.cells;
    
    //TODO: Verificar que el id se recoge correctamente en otros navegadores
    
    celdas[1].innerText = datos.nombre = form.nombre.value;
    celdas[2].innerText = datos.apellidos = form.apellidos.value;
    celdas[3].innerText = datos.email = form.email.value;
    celdas[4].innerText = datos.dni = form.dni.value;
    f = new Date(datos.fechaDeNacimiento = form['fecha-de-nacimiento'].value);
    datos.nacionalidad = form.nacionalidad.value;
    
    celdas[5].innerText = moment(f).format('DD/MM/YYYY');
        //f.getDate() + '/' + (f.getMonth() + 1) + '/' + f.getFullYear();
    
    nac = form.nacionalidad;
    
    celdas[6].innerText = nac.options[nac.selectedIndex].innerText;
}

function crearFilaTabla() {
    'use strict';
    
    var celda, n, fila, tbody, id = ++ultimoId;
    
    tbody = $('#listado table tbody')[0];
    
    fila = tbody.insertRow();
    
    fila.id = 'id' + id;
    
    fila.insertCell().outerHTML = '<th>' + id + '</th>';
    
    for (n = 1; n <= 7; n++) {
        celda = fila.insertCell();
    }
    
    celda.innerHTML = '<a class="btn btn-primary" href="?opcion=editar&id=' + id + '">Editar</a> <a class="btn btn-danger" href="?opcion=borrar&id=' + id + '">Borrar</a>';
    
    $(celda).find('a').click(function (e) {
        gestionEventoClickHrefOpcion(e, this);
    });
    
    return id;
}

//TODO: Generar función para id de fila de tabla

function borrarFilaTabla(id) {
    'use strict';
    $('#id' + id).remove();
}


$(function () {
    'use strict';
    
    $('table').DataTable();
    
    var $form;
    
    $form = $('#formulario');
    
    $('#detalle').hide();

    $('a[href*=opcion]').click(function (e) {
        gestionEventoClickHrefOpcion(e, this);
    });

    $form.submit(function (e) {
        //TODO: Quitar esto si se va a usar con un servidor normal
        e.preventDefault();
        e.stopPropagation();
        
        var errorFecha, errorDni, $fecha, $dni, $dniError, $fechaError, opcion, id;
        
        $fecha = $('#fecha-de-nacimiento');
        $dni = $('#dni');
        $dniError = $('#dni-error');
        $fechaError = $('#fecha-error');
        
        if (!esDeEdadLegalParaTrabajar(new Date($fecha.val()))) {
            errorFecha = 'No tiene edad legal para trabajar. Sólo es válido fechas de nacimiento de personas entre 18 y 65 años';
        } else {
            errorFecha = '';
        }
        
        if (!esDniValido($dni.val())) {
            errorDni = 'La letra de DNI no concuerda con el número';
        } else if (esDniExtranjero($dni.val()) && $('#nacionalidad').val() !== '2') {
            errorDni = 'Si el DNI es extranjero la nacionalidad debe ser extranjero';
        } else if (!esDniExtranjero($dni.val()) && $('#nacionalidad').val() !== '1') {
            errorDni = 'Si el DNI es español la nacionalidad debe ser española';
        } else {
            errorDni = '';
        }
        
        $dni[0].setCustomValidity(errorDni);
        $fecha[0].setCustomValidity(errorFecha);
        
        if (!$form[0].checkValidity()) {
            
            e.preventDefault();
            e.stopPropagation();
            
            if ($dni[0].validity.patternMismatch) {
                errorDni = 'El DNI tiene que tener el formato 12345678A';
            } else if ($dni[0].validity.valueMissing) {
                errorDni = 'Es obligatorio rellenar el DNI';
            }
            
            if ($fecha[0].validity.valueMissing) {
                errorFecha = 'Es obligatorio indicar la fecha de nacimiento';
            }
            
            $dniError.text(errorDni);
            $fechaError.text(errorFecha);
            
            $form.addClass('was-validated');
            
            return false;
        }
        
        //CORRECTO
        
        opcion = $('#btn-enviar').val();
        id = document.forms[0].id.value;
        
        switch (opcion) {
        case 'anadir':
            id = crearFilaTabla();
            formularioAFilaTabla(id);
            
            break;
        case 'editar':
            formularioAFilaTabla(id);
                
            break;
        case 'borrar':
            borrarFilaTabla(id);
                
            break;
        default:
            console.error('opcion', opcion);
        }
        
        $('#detalle').hide();
    });
    
});
