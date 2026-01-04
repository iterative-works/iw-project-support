{
  description = "Posuzovani shody";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-24.11";
    flake-utils.url = "github:numtide/flake-utils";
    eid-pki.url = "git+https://gitlab.e-bs.cz/mph/eid-nix-pki.git?ref=main";
    flake-compat = {
      url = "github:edolstra/flake-compat";
      flake = false;
    };
  };

  outputs = { self, nixpkgs, flake-utils, eid-pki, ... }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = import nixpkgs {
          inherit system;
          overlays = [ eid-pki.overlays.jdk17 ];
        };
      in {
        devShell = with pkgs;
          mkShell {
            buildInputs = [ jre ammonite coursier sbt scalafmt ];
          };
      });
}
