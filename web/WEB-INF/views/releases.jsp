<%-- 
    Document   : releases
    Created on : 09-Mar-2014, 16:04:54
    Author     : Chris Pratt <www.chrispdevelopment.co.uk>
--%>
<%@page import="entities.music.Song"%>
<%@page import="entities.music.SongList"%>
<%
    SongList songs = (SongList) request.getAttribute("songs");
%>
<div class="col-md-9">
    <div class="content" id="portsMargin">
        <%
            for (int pos = 0; pos < songs.size(); pos++) {
                Song song = songs.getSongAt(pos);
        %>
        <div class="row" id="songInfo">
            <div class="col-md-3">
                <%= song.getName() %>
            </div>
            <div class="col-md-3">
                <%= song.getCreatedByName() %>
            </div>
            <div class="col-md-3">
                <%= song.getReleaseDate() %>
            </div>
        </div>
        <div id="jquery_jplayer_<%= song.getSongID() %>" class="jp-jplayer"></div>

        <div id="jp_container_<%= song.getSongID() %>">
            <div class="jp-gui ui-widget ui-widget-content ui-corner-all">
                <ul class="jp-gui ul">
                    <li class="jp-play ui-state-default ui-corner-all"><a href="javascript:;" class="jp-play ui-icon ui-icon-play" tabindex="1" title="play">play</a></li>
                    <li class="jp-pause ui-state-default ui-corner-all"><a href="javascript:;" class="jp-pause ui-icon ui-icon-pause" tabindex="1" title="pause">pause</a></li>
                    <li class="jp-stop ui-state-default ui-corner-all"><a href="javascript:;" class="jp-stop ui-icon ui-icon-stop" tabindex="1" title="stop">stop</a></li>
                    <li class="jp-repeat ui-state-default ui-corner-all"><a href="javascript:;" class="jp-repeat ui-icon ui-icon-refresh" tabindex="1" title="repeat">repeat</a></li>
                    <li class="jp-repeat-off ui-state-default ui-state-active ui-corner-all"><a href="javascript:;" class="jp-repeat-off ui-icon ui-icon-refresh" tabindex="1" title="repeat off">repeat off</a></li>
                    <li class="jp-mute ui-state-default ui-corner-all"><a href="javascript:;" class="jp-mute ui-icon ui-icon-volume-off" tabindex="1" title="mute">mute</a></li>
                    <li class="jp-unmute ui-state-default ui-state-active ui-corner-all"><a href="javascript:;" class="jp-unmute ui-icon ui-icon-volume-off" tabindex="1" title="unmute">unmute</a></li>
                    <li class="jp-volume-max ui-state-default ui-corner-all"><a href="javascript:;" class="jp-volume-max ui-icon ui-icon-volume-on" tabindex="1" title="max volume">max volume</a></li>
                </ul>
                <div class="jp-progress-slider"></div>
                <div class="jp-volume-slider"></div>
                <div class="jp-current-time"></div>
                <div class="jp-duration"></div>
                <div class="jp-clearboth"></div>
            </div>
            <div class="jp-no-solution">
                <span>Update Required</span>
                To play the media you will need to either update your browser to a recent version or update your <a href="http://get.adobe.com/flashplayer/" target="_blank">Flash plugin</a>.
            </div>
        </div>
        <script>
            var myPlayer1 = $("#jquery_jplayer_<%= song.getSongID() %>"),
                    myPlayerData,
                    fixFlash_mp4, // Flag: The m4a and m4v Flash player gives some old currentTime values when changed.
                    fixFlash_mp4_id, // Timeout ID used with fixFlash_mp4
                    ignore_timeupdate, // Flag used with fixFlash_mp4
                    options = {
                        ready: function(event) {
                            // Hide the volume slider on mobile browsers. ie., They have no effect.
                            if (event.jPlayer.status.noVolume) {
                                // Add a class and then CSS rules deal with it.
                                $(".jp-gui").addClass("jp-no-volume");
                            }
                            // Determine if Flash is being used and the mp4 media type is supplied. BTW, Supplying both mp3 and mp4 is pointless.
                            fixFlash_mp4 = event.jPlayer.flash.used && /m4a|m4v/.test(event.jPlayer.options.supplied);
                            // Setup the player with media.
                            $(this).jPlayer("setMedia", {
                                m4a: "<%=request.getContextPath()%>/assets/songs/<%= song.getSongLink() %>.mp4"
                            });
                        },
                        timeupdate: function(event) {
                            if (!ignore_timeupdate) {
                                myControl1.progress.slider("value", event.jPlayer.status.currentPercentAbsolute);
                            }
                        },
                        volumechange: function(event) {
                            if (event.jPlayer.options.muted) {
                                myControl1.volume.slider("value", 0);
                            } else {
                                myControl1.volume.slider("value", event.jPlayer.options.volume);
                            }
                        },
                        swfPath: "../js",
                        supplied: "m4a, oga",
                        cssSelectorAncestor: "#jp_container_<%= song.getSongID() %>",
                        wmode: "window",
                        keyEnabled: true
                    },
            myControl1 = {
                progress: $(options.cssSelectorAncestor + " .jp-progress-slider"),
                volume: $(options.cssSelectorAncestor + " .jp-volume-slider")
            };

            // Instance jPlayer
            myPlayer1.jPlayer(options);

            // A pointer to the jPlayer data object
            myPlayerData1 = myPlayer1.data("jPlayer");

            // Define hover states of the buttons
            $('.jp-gui ul li').hover(
                    function() {
                        $(this).addClass('ui-state-hover');
                    },
                    function() {
                        $(this).removeClass('ui-state-hover');
                    }
            );

            // Create the progress slider control
            myControl1.progress.slider({
                animate: "fast",
                max: 100,
                range: "min",
                step: 0.1,
                value: 0,
                slide: function(event, ui) {
                    var sp = myPlayerData1.status.seekPercent;
                    if (sp > 0) {
                        // Apply a fix to mp4 formats when the Flash is used.
                        if (fixFlash_mp4) {
                            ignore_timeupdate = true;
                            clearTimeout(fixFlash_mp4_id);
                            fixFlash_mp4_id = setTimeout(function() {
                                ignore_timeupdate = false;
                            }, 1000);
                        }
                        // Move the play-head to the value and factor in the seek percent.
                        myPlayer1.jPlayer("playHead", ui.value * (100 / sp));
                    } else {
                        // Create a timeout to reset this slider to zero.
                        setTimeout(function() {
                            myControl1.progress.slider("value", 0);
                        }, 0);
                    }
                }
            });

            // Create the volume slider control
            myControl1.volume.slider({
                animate: "fast",
                max: 1,
                range: "min",
                step: 0.01,
                value: $.jPlayer.prototype.options.volume,
                slide: function(event, ui) {
                    myPlayer1.jPlayer("option", "muted", false);
                    myPlayer1.jPlayer("option", "volume", ui.value);
                }
            });
        </script>
        <% }%>
    </div>
</div>
