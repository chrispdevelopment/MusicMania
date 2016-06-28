$(document).ready(function() {
    $('#loginSubmit').click(function() {
        $('#loginForm').submit();
    });

    $('#editArticleSubmit').click(function() {
        $('#editArticleForm').submit();
    });

    $('#addCommentSubmit').click(function() {
        $('#addCommentForm').submit();
    });

    $('#editCommentSubmit').click(function() {
        $('#editCommentForm').submit();
    });

    $('#regSubmit').click(function() {
        $('#regForm').submit();
    });

    $('#passwordSubmit').click(function() {
        $('#passwordForm').submit();
    });

    $('#emailSubmit').click(function() {
        $('#emailForm').submit();
    });
    
    $('#accessSubmit').click(function() {
        $('#accessForm').submit();
    });

    $('#infoSubmit').click(function() {
        $('#infoForm').submit();
    });

    $('#addInfoSubmit').click(function() {
        $('#addInfoForm').submit();
    });

    $('#addMoneySubmit').click(function() {
        $('#addMoneyForm').submit();
    });

    $('#cardDetailsSubmit').click(function() {
        $('#cardDetailsForm').submit();
    });

    $('#delAddressSubmit').click(function() {
        $('#delAddressForm').submit();
    });

    $('#bilAddressSubmit').click(function() {
        $('#bilAddressForm').submit();
    });

    $('#addSubmit').click(function() {
        $('#addForm').submit();
    });

    $('#editSubmit').click(function() {
        $('#editForm').submit();
    });
    
    $('#addTypeSubmit').click(function() {
        $('#addTypeForm').submit();
    });

    $('#editTypeSubmit').click(function() {
        $('#editTypeForm').submit();
    });
    
    $('#addSizeSubmit').click(function() {
        $('#addSizeForm').submit();
    });

    $('#editSizeSubmit').click(function() {
        $('#editSizeForm').submit();
    });
    
    $('#addGenreSubmit').click(function() {
        $('#addGenreForm').submit();
    });

    $('#editGenreSubmit').click(function() {
        $('#editGenreForm').submit();
    });
    
    $('#addLocationSubmit').click(function() {
        $('#addLocationForm').submit();
    });

    $("input:radio[name=select]").click(function() {
        var value = $(this).val();
        var page = $("#page").val();
        var pageLowerCase = page.toLowerCase();
        var context = $("#context").val();
        $("#edit").attr("href", context + "/adminProfiles/edit" + page + "/" + value);
        $("#delete").attr("href", context + "/admin/delete" + page + "/" + value);
    });
    
    $("input:radio[name=selectType]").click(function() {
        var value = $(this).val();
        var context = $("#context").val();
        var typeName = $("#typeName" + value).val();
        $("#deleteType").attr("href", context + "/admin/deleteType/" + value);
        $("#editTypeName").val(typeName);
        $("#typeID").val(value);
    });
    
    $("input:radio[name=selectSize]").click(function() {
        var value = $(this).val();
        var context = $("#context").val();
        var typeName = $("#sizeName" + value).val();
        $("#deleteSize").attr("href", context + "/admin/deleteSize/" + value);
        $("#editSizeName").val(typeName);
        $("#sizeID").val(value);
    });
    
    $("input:radio[name=selectGenre]").click(function() {
        var value = $(this).val();
        var context = $("#context").val();
        var typeName = $("#genreName" + value).val();
        $("#deleteGenre").attr("href", context + "/admin/deleteGenre/" + value);
        $("#editGenreName").val(typeName);
        $("#genreID").val(value);
    });
    
    $("input:radio[name=selectLocation]").click(function() {
        var value = $(this).val();
        var context = $("#context").val();
        $("#editLocation").attr("href", context + "/adminProfiles/editLocation/" + value);
        $("#deleteLocation").attr("href", context + "/admin/deleteLocation/" + value);
    });
    
    $("input:radio[name=selectSong]").click(function() {
        var value = $(this).val();
        var artistID = $('#artistID').val()
        var type = $('#deleteType').val()
        var context = $("#context").val();
        $("#delete").attr("href", context + "/adminProfiles/deleteSong/" + value + "/" + artistID + "/" + type);
    });
    
    $("input:radio[name=selectArtist]").click(function() {
        var value = $(this).val();
        var type = $('#type').val()
        var page = $("#page").val();
        var context = $("#context").val();
        $("#edit").attr("href", context + "/adminProfiles/editArtist/" + value + "/" + type);
        $("#delete").attr("href", context + "/admin/" + page + "/" + value);
    });

    $('#band').hide();
    $('#artist').hide();

    $("#artistSelect").change(function() {
        var list_value = $(this).val();
        if (list_value === "Artist") {
            $('#artist').show();
            $('#band').hide();
        } else {
            $('#band').show();
            $('#artist').hide();
        }
    });

    $("#editModal").on('show.bs.modal', function() {
       var artistType = $("#artistType").val(); 
        if (artistType === "artist") {
            $('#artist').show();
        } else {
            $('#band').show();
        }
    });

});





