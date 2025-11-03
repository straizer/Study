FROM mcr.microsoft.com/devcontainers/cpp:debian

ENV DEBIAN_FRONTEND=noninteractive

# base tools
RUN apt-get update && apt-get upgrade && \
    apt-get install -y --no-install-recommends openssh-server sudo && \
    rm -rf /var/lib/apt/lists/*

# CLion 2025.2.4
ARG CLION_TARBALL_URL=https://download.jetbrains.com/cpp/CLion-2025.2.4.tar.gz
RUN mkdir -p /opt && \
    curl -fSL "$CLION_TARBALL_URL" | tar -xz -C /opt && \
    ln -s /opt/clion-* /opt/clion && \
    ln -s /opt/clion/bin/remote-dev-server.sh /usr/local/bin/clion-remote

# user
RUN useradd -m dev && passwd -d dev && \
    echo "dev ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers
RUN mkdir -p /home/dev/workspace && chown -R dev:dev /home/dev

# ssh config
RUN printf "PermitEmptyPasswords yes\nUsePAM no\n" >> /etc/ssh/sshd_config
RUN mkdir -p /var/run/sshd

# register CLion for *dev* so Gateway sees it
RUN su - dev -c "/opt/clion/bin/remote-dev-server.sh registerBackendLocationForGateway"

WORKDIR /home/dev/workspace
EXPOSE 22

HEALTHCHECK --interval=10s --timeout=3s --start-period=5s --retries=3 \
  CMD ss -tln | grep -q ':22 ' || exit 1

CMD ["/usr/sbin/sshd", "-D"]