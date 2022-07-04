{ pkgs ? import <nixpkgs> {
  overlays = [
    (final: prev: rec {
      jre = prev.jdk$java_version$_headless;
      jdk = jre;
    })
  ];
} }:

with pkgs;
mkShell { buildInputs = [ jre ammonite coursier bloop sbt scalafmt ]; }
